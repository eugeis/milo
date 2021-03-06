package org.eclipse.milo.opcua.sdk.server.model.nodes.objects;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.api.nodes.ObjectNode;
import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.model.nodes.variables.TwoStateVariableNode;
import org.eclipse.milo.opcua.sdk.server.model.types.objects.ExclusiveLimitAlarmType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;

public class ExclusiveLimitAlarmNode extends LimitAlarmNode implements ExclusiveLimitAlarmType {
  public ExclusiveLimitAlarmNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                                 LocalizedText displayName, LocalizedText description, UInteger writeMask,
                                 UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public ExclusiveLimitAlarmNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                                 LocalizedText displayName, LocalizedText description, UInteger writeMask,
                                 UInteger userWriteMask, UByte eventNotifier) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
  }

  public TwoStateVariableNode getActiveStateNode() {
    Optional<VariableNode> component = getVariableComponent("http://opcfoundation.org/UA/", "ActiveState");
    return (TwoStateVariableNode) component.orElse(null);
  }

  public LocalizedText getActiveState() {
    Optional<VariableNode> component = getVariableComponent("ActiveState");
    return component.map(node -> (LocalizedText) node.getValue().getValue().getValue()).orElse(null);
  }

  public void setActiveState(LocalizedText value) {
    getVariableComponent("ActiveState").ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
  }

  public ExclusiveLimitStateMachineNode getLimitStateNode() {
    Optional<ObjectNode> component = getObjectComponent("http://opcfoundation.org/UA/", "LimitState");
    return (ExclusiveLimitStateMachineNode) component.orElse(null);
  }
}
