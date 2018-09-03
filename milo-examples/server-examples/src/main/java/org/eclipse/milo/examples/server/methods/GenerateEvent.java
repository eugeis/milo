package org.eclipse.milo.examples.server.methods;

import java.util.UUID;

import org.eclipse.milo.opcua.sdk.server.OpcUaServer;
import org.eclipse.milo.opcua.sdk.server.annotations.UaInputArgument;
import org.eclipse.milo.opcua.sdk.server.annotations.UaMethod;
import org.eclipse.milo.opcua.sdk.server.model.nodes.objects.BaseEventNode;
import org.eclipse.milo.opcua.sdk.server.nodes.EventFactory;
import org.eclipse.milo.opcua.sdk.server.util.AnnotationBasedInvocationHandler;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.UaRuntimeException;
import org.eclipse.milo.opcua.stack.core.types.builtin.ByteString;
import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;

public class GenerateEvent {

    private final OpcUaServer server;

    public GenerateEvent(OpcUaServer server) {
        this.server = server;
    }

    @UaMethod
    public void invoke(
        AnnotationBasedInvocationHandler.InvocationContext context,
        @UaInputArgument(
            name = "eventTypeId",
            description = "NodeId of the TypeDefinition of the event to generate.")
            NodeId eventTypeId) {

        EventFactory eventFactory = server.getEventFactory();

        try {
            BaseEventNode eventNode = eventFactory.createEvent(
                new NodeId(1, UUID.randomUUID()),
                new QualifiedName(1, "foo"),
                LocalizedText.english("foo"),
                eventTypeId
            );

            eventNode.setEventId(ByteString.of(new byte[]{0, 1, 2, 3}));
            eventNode.setEventType(Identifiers.BaseEventType);
            eventNode.setSourceNode(NodeId.NULL_VALUE);
            eventNode.setSourceName("");
            eventNode.setTime(DateTime.now());
            eventNode.setReceiveTime(DateTime.NULL_VALUE);
            eventNode.setMessage(LocalizedText.english("event message!"));
            eventNode.setSeverity(ushort(2));

            server.getEventBus().post(eventNode);
        } catch (UaRuntimeException e) {
            context.setFailure(new UaException(e.getStatusCode()));
        }
    }

}