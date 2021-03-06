package org.eclipse.milo.opcua.sdk.server.model.nodes.objects;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.model.nodes.variables.PropertyNode;
import org.eclipse.milo.opcua.sdk.server.model.types.objects.ExclusiveDeviationAlarmType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;

public class ExclusiveDeviationAlarmNode extends ExclusiveLimitAlarmNode implements ExclusiveDeviationAlarmType {
  public ExclusiveDeviationAlarmNode(UaNodeContext context, NodeId nodeId,
                                     QualifiedName browseName, LocalizedText displayName, LocalizedText description,
                                     UInteger writeMask, UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public ExclusiveDeviationAlarmNode(UaNodeContext context, NodeId nodeId,
                                     QualifiedName browseName, LocalizedText displayName, LocalizedText description,
                                     UInteger writeMask, UInteger userWriteMask, UByte eventNotifier) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
  }

  public PropertyNode getSetpointNodeNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(ExclusiveDeviationAlarmType.SETPOINT_NODE);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public NodeId getSetpointNode() {
    Optional<NodeId> propertyValue = getProperty(ExclusiveDeviationAlarmType.SETPOINT_NODE);
    return propertyValue.orElse(null);
  }

  public void setSetpointNode(NodeId value) {
    setProperty(ExclusiveDeviationAlarmType.SETPOINT_NODE, value);
  }
}
