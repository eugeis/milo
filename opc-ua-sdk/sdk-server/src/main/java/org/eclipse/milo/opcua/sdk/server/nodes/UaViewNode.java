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

package org.eclipse.milo.opcua.sdk.server.nodes;

import javax.annotation.Nullable;

import org.eclipse.milo.opcua.sdk.core.ValueRanks;
import org.eclipse.milo.opcua.sdk.core.model.QualifiedProperty;
import org.eclipse.milo.opcua.sdk.core.model.UaOptional;
import org.eclipse.milo.opcua.sdk.server.api.nodes.ViewNode;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;
import org.eclipse.milo.opcua.stack.core.util.Namespaces;

public class UaViewNode extends UaNode implements ViewNode {

    private volatile Boolean containsNoLoops;
    private volatile UByte eventNotifier;

    public UaViewNode(
        UaNodeContext context,
        NodeId nodeId,
        QualifiedName browseName,
        LocalizedText displayName,
        LocalizedText description,
        UInteger writeMask,
        UInteger userWriteMask,
        Boolean containsNoLoops,
        UByte eventNotifier) {

        super(context, nodeId, NodeClass.View,
            browseName, displayName, description, writeMask, userWriteMask);

        this.containsNoLoops = containsNoLoops;
        this.eventNotifier = eventNotifier;
    }

    @Override
    public Boolean getContainsNoLoops() {
        return containsNoLoops;
    }

    @Override
    public UByte getEventNotifier() {
        return eventNotifier;
    }

    @Override
    public synchronized void setContainsNoLoops(Boolean containsNoLoops) {
        this.containsNoLoops = containsNoLoops;

        fireAttributeChanged(AttributeId.ContainsNoLoops, containsNoLoops);
    }

    @Override
    public synchronized void setEventNotifier(UByte eventNotifier) {
        this.eventNotifier = eventNotifier;

        fireAttributeChanged(AttributeId.EventNotifier, eventNotifier);
    }

    @Nullable
    @UaOptional("NodeVersion")
    public String getNodeVersion() {
        return getProperty(NodeVersion).orElse(null);
    }

    @Nullable
    @UaOptional("ViewVersion")
    public UInteger getViewVersion() {
        return getProperty(ViewVersion).orElse(null);
    }

    public void setNodeVersion(String nodeVersion) {
        setProperty(NodeVersion, nodeVersion);
    }

    public void setViewVersion(UInteger viewVersion) {
        setProperty(ViewVersion, viewVersion);
    }

    public static final QualifiedProperty<String> NodeVersion = new QualifiedProperty<>(
        Namespaces.OPC_UA,
        "NodeVersion",
        Identifiers.String,
        ValueRanks.Scalar,
        String.class
    );

    public static final QualifiedProperty<UInteger> ViewVersion = new QualifiedProperty<>(
        Namespaces.OPC_UA,
        "ViewVersion",
        Identifiers.UInt32,
        ValueRanks.Scalar,
        UInteger.class
    );

}
