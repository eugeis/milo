package org.eclipse.milo.opcua.sdk.server.model.nodes.objects;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.model.nodes.variables.StateVariableNode;
import org.eclipse.milo.opcua.sdk.server.model.nodes.variables.TransitionVariableNode;
import org.eclipse.milo.opcua.sdk.server.model.types.objects.StateMachineType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;

public class StateMachineNode extends BaseObjectNode implements StateMachineType {
  public StateMachineNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                          LocalizedText displayName, LocalizedText description, UInteger writeMask,
                          UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public StateMachineNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                          LocalizedText displayName, LocalizedText description, UInteger writeMask,
                          UInteger userWriteMask, UByte eventNotifier) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
  }

  public StateVariableNode getCurrentStateNode() {
    Optional<VariableNode> component = getVariableComponent("http://opcfoundation.org/UA/", "CurrentState");
    return (StateVariableNode) component.orElse(null);
  }

  public LocalizedText getCurrentState() {
    Optional<VariableNode> component = getVariableComponent("CurrentState");
    return component.map(node -> (LocalizedText) node.getValue().getValue().getValue()).orElse(null);
  }

  public void setCurrentState(LocalizedText value) {
    getVariableComponent("CurrentState").ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
  }

  public TransitionVariableNode getLastTransitionNode() {
    Optional<VariableNode> component = getVariableComponent("http://opcfoundation.org/UA/", "LastTransition");
    return (TransitionVariableNode) component.orElse(null);
  }

  public LocalizedText getLastTransition() {
    Optional<VariableNode> component = getVariableComponent("LastTransition");
    return component.map(node -> (LocalizedText) node.getValue().getValue().getValue()).orElse(null);
  }

  public void setLastTransition(LocalizedText value) {
    getVariableComponent("LastTransition").ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
  }
}
