package org.eclipse.milo.opcua.sdk.server.model.nodes.objects;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.model.nodes.variables.PropertyNode;
import org.eclipse.milo.opcua.sdk.server.model.types.objects.AuditConditionCommentEventType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.ByteString;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;

public class AuditConditionCommentEventNode extends AuditConditionEventNode implements AuditConditionCommentEventType {
  public AuditConditionCommentEventNode(UaNodeContext context, NodeId nodeId,
                                        QualifiedName browseName, LocalizedText displayName, LocalizedText description,
                                        UInteger writeMask, UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public AuditConditionCommentEventNode(UaNodeContext context, NodeId nodeId,
                                        QualifiedName browseName, LocalizedText displayName, LocalizedText description,
                                        UInteger writeMask, UInteger userWriteMask, UByte eventNotifier) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
  }

  public PropertyNode getEventIdNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(AuditConditionCommentEventType.EVENT_ID);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public ByteString getEventId() {
    Optional<ByteString> propertyValue = getProperty(AuditConditionCommentEventType.EVENT_ID);
    return propertyValue.orElse(null);
  }

  public void setEventId(ByteString value) {
    setProperty(AuditConditionCommentEventType.EVENT_ID, value);
  }

  public PropertyNode getCommentNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(AuditConditionCommentEventType.COMMENT);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public LocalizedText getComment() {
    Optional<LocalizedText> propertyValue = getProperty(AuditConditionCommentEventType.COMMENT);
    return propertyValue.orElse(null);
  }

  public void setComment(LocalizedText value) {
    setProperty(AuditConditionCommentEventType.COMMENT, value);
  }
}
