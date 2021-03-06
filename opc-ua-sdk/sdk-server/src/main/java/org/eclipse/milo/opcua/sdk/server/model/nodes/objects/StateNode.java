package org.eclipse.milo.opcua.sdk.server.model.nodes.objects;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.model.nodes.variables.PropertyNode;
import org.eclipse.milo.opcua.sdk.server.model.types.objects.StateType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;

public class StateNode extends BaseObjectNode implements StateType {
  public StateNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                   LocalizedText displayName, LocalizedText description, UInteger writeMask,
                   UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public StateNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                   LocalizedText displayName, LocalizedText description, UInteger writeMask,
                   UInteger userWriteMask, UByte eventNotifier) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
  }

  public PropertyNode getStateNumberNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(StateType.STATE_NUMBER);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public UInteger getStateNumber() {
    Optional<UInteger> propertyValue = getProperty(StateType.STATE_NUMBER);
    return propertyValue.orElse(null);
  }

  public void setStateNumber(UInteger value) {
    setProperty(StateType.STATE_NUMBER, value);
  }
}
