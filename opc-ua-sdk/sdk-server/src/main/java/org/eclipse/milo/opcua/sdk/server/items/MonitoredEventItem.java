/*
 * Copyright (c) 2016 Kevin Herron
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *   http://www.eclipse.org/org/documents/edl-v10.html.
 */

package org.eclipse.milo.opcua.sdk.server.items;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nonnull;

import org.eclipse.milo.opcua.sdk.server.OpcUaServer;
import org.eclipse.milo.opcua.sdk.server.Session;
import org.eclipse.milo.opcua.sdk.server.api.EventItem;
import org.eclipse.milo.opcua.sdk.server.events.EventContentFilter;
import org.eclipse.milo.opcua.sdk.server.events.FilterContext;
import org.eclipse.milo.opcua.sdk.server.model.nodes.objects.BaseEventNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.StatusCodes;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.serialization.UaStructure;
import org.eclipse.milo.opcua.stack.core.types.builtin.ByteString;
import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;
import org.eclipse.milo.opcua.stack.core.types.builtin.ExtensionObject;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.ContentFilter;
import org.eclipse.milo.opcua.stack.core.types.structured.EventFieldList;
import org.eclipse.milo.opcua.stack.core.types.structured.EventFilter;
import org.eclipse.milo.opcua.stack.core.types.structured.EventFilterResult;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.eclipse.milo.opcua.stack.core.types.structured.SimpleAttributeOperand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;

public class MonitoredEventItem extends BaseMonitoredItem<Variant[]> implements EventItem {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile EventFilter filter;
    private volatile EventFilterResult filterResult;

    private final AtomicBoolean eventOverflow = new AtomicBoolean(false);

    private final FilterContext filterContext;

    public MonitoredEventItem(
        OpcUaServer server,
        Session session,
        UInteger id,
        UInteger subscriptionId,
        ReadValueId readValueId,
        MonitoringMode monitoringMode,
        TimestampsToReturn timestamps,
        UInteger clientHandle,
        double samplingInterval,
        UInteger queueSize,
        boolean discardOldest,
        ExtensionObject filter) throws UaException {

        super(server, session, id, subscriptionId, readValueId, monitoringMode,
            timestamps, clientHandle, samplingInterval, queueSize, discardOldest);

        filterContext = new FilterContext() {
            @Override
            public OpcUaServer getServer() {
                return server;
            }

            @Override
            public Optional<Session> getSession() {
                return Optional.of(session);
            }
        };

        installFilter(filter);
    }

    @Override
    public void onEvent(BaseEventNode eventNode) {
        try {
            ContentFilter whereClause = filter.getWhereClause();

            boolean matches = EventContentFilter.evaluate(
                filterContext,
                whereClause,
                eventNode
            );

            if (matches) {
                enqueue(selectEventFields(eventNode));
            }
        } catch (UaException e) {
            logger.error("Filter evaluation failed: {}", e.getMessage(), e);
        }
    }


    @Nonnull
    private Variant[] selectEventFields(BaseEventNode eventNode) {
        SimpleAttributeOperand[] selectClauses = filter.getSelectClauses();

        if (selectClauses != null) {
            return EventContentFilter.select(
                filterContext,
                selectClauses,
                eventNode
            );
        } else {
            return new Variant[0];
        }
    }

    @Override
    protected synchronized void enqueue(Variant[] value) {
        if (queue.size() < queue.maxSize()) {
            queue.add(value);
        } else {
            if (getQueueSize() > 1) {
                eventOverflow.set(true);
            }

            if (discardOldest) {
                queue.add(value);
            } else {
                queue.set(queue.maxSize() - 1, value);
            }
        }
    }

    @Override
    public synchronized boolean getNotifications(List<UaStructure> notifications, int max) {
        if (eventOverflow.compareAndSet(true, false)) {
            Variant[] eventFields = generateOverflowEventFields();

            if (discardOldest) {
                // insert overflow event at beginning
                notifications.add(wrapQueueValue(eventFields));

                return super.getNotifications(notifications, max);
            } else {
                // insert overflow event at end
                boolean more = super.getNotifications(notifications, max);
                notifications.add(wrapQueueValue(eventFields));

                return more;
            }
        } else {
            return super.getNotifications(notifications, max);
        }
    }

    @Nonnull
    private Variant[] generateOverflowEventFields() {
        try {
            UUID eventId = UUID.randomUUID();

            BaseEventNode overflowEvent = server.getEventFactory().createEvent(
                new NodeId(1, eventId),
                Identifiers.EventQueueOverflowEventType
            );

            overflowEvent.setBrowseName(new QualifiedName(1, "EventQueueOverflow"));
            overflowEvent.setDisplayName(LocalizedText.english("EventQueueOverflow"));

            ByteBuffer buffer = ByteBuffer.allocate(64);
            buffer.putLong(eventId.getMostSignificantBits());
            buffer.putLong(eventId.getLeastSignificantBits());

            overflowEvent.setEventId(ByteString.of(buffer.array()));
            overflowEvent.setEventType(Identifiers.EventQueueOverflowEventType);
            overflowEvent.setSourceNode(Identifiers.Server);
            overflowEvent.setSourceName("Server");
            overflowEvent.setTime(DateTime.now());
            overflowEvent.setReceiveTime(DateTime.NULL_VALUE);
            overflowEvent.setMessage(LocalizedText.english("Event Queue Overflow"));
            overflowEvent.setSeverity(ushort(0));

            return selectEventFields(overflowEvent);
        } catch (UaException e) {
            logger.error("Error creating overflow event: {}", e.getMessage(), e);

            return new Variant[0];
        }
    }

    @Override
    public ExtensionObject getFilterResult() {
        return ExtensionObject.encode(filterResult);
    }

    @Override
    protected void installFilter(ExtensionObject filterXo) throws UaException {
        Object filterObject = filterXo != null ? filterXo.decode() : null;

        if (filterObject instanceof EventFilter) {
            this.filter = (EventFilter) filterObject;

            filterResult = EventContentFilter.validate(filterContext, filter);
        } else {
            throw new UaException(StatusCodes.Bad_MonitoredItemFilterInvalid);
        }
    }

    @Override
    protected EventFieldList wrapQueueValue(Variant[] value) {
        return new EventFieldList(uint(getClientHandle()), value);
    }

    @Override
    public boolean isSamplingEnabled() {
        return getMonitoringMode() != MonitoringMode.Disabled;
    }

}
