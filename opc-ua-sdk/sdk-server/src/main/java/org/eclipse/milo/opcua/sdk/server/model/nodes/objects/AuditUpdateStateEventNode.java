package org.eclipse.milo.opcua.sdk.server.model.nodes.objects;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.model.nodes.variables.PropertyNode;
import org.eclipse.milo.opcua.sdk.server.model.types.objects.AuditUpdateStateEventType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;

public class AuditUpdateStateEventNode extends AuditUpdateMethodEventNode implements AuditUpdateStateEventType {
  public AuditUpdateStateEventNode(UaNodeContext context, NodeId nodeId,
                                   QualifiedName browseName, LocalizedText displayName, LocalizedText description,
                                   UInteger writeMask, UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public AuditUpdateStateEventNode(UaNodeContext context, NodeId nodeId,
                                   QualifiedName browseName, LocalizedText displayName, LocalizedText description,
                                   UInteger writeMask, UInteger userWriteMask, UByte eventNotifier) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
  }

  public PropertyNode getOldStateIdNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(AuditUpdateStateEventType.OLD_STATE_ID);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public Object getOldStateId() {
    Optional<Object> propertyValue = getProperty(AuditUpdateStateEventType.OLD_STATE_ID);
    return propertyValue.orElse(null);
  }

  public void setOldStateId(Object value) {
    setProperty(AuditUpdateStateEventType.OLD_STATE_ID, value);
  }

  public PropertyNode getNewStateIdNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(AuditUpdateStateEventType.NEW_STATE_ID);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public Object getNewStateId() {
    Optional<Object> propertyValue = getProperty(AuditUpdateStateEventType.NEW_STATE_ID);
    return propertyValue.orElse(null);
  }

  public void setNewStateId(Object value) {
    setProperty(AuditUpdateStateEventType.NEW_STATE_ID, value);
  }
}
