package org.eclipse.milo.opcua.sdk.server.model.nodes.objects;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.model.nodes.variables.PropertyNode;
import org.eclipse.milo.opcua.sdk.server.model.types.objects.SystemStatusChangeEventType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.ServerState;

public class SystemStatusChangeEventNode extends SystemEventNode implements SystemStatusChangeEventType {
  public SystemStatusChangeEventNode(UaNodeContext context, NodeId nodeId,
                                     QualifiedName browseName, LocalizedText displayName, LocalizedText description,
                                     UInteger writeMask, UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public SystemStatusChangeEventNode(UaNodeContext context, NodeId nodeId,
                                     QualifiedName browseName, LocalizedText displayName, LocalizedText description,
                                     UInteger writeMask, UInteger userWriteMask, UByte eventNotifier) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
  }

  public PropertyNode getSystemStateNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(SystemStatusChangeEventType.SYSTEM_STATE);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public ServerState getSystemState() {
    Optional<ServerState> propertyValue = getProperty(SystemStatusChangeEventType.SYSTEM_STATE);
    return propertyValue.orElse(null);
  }

  public void setSystemState(ServerState value) {
    setProperty(SystemStatusChangeEventType.SYSTEM_STATE, value);
  }
}
