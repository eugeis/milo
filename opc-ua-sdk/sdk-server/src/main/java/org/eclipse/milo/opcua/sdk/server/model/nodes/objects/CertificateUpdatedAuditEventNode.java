package org.eclipse.milo.opcua.sdk.server.model.nodes.objects;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.model.nodes.variables.PropertyNode;
import org.eclipse.milo.opcua.sdk.server.model.types.objects.CertificateUpdatedAuditEventType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;

public class CertificateUpdatedAuditEventNode extends AuditUpdateMethodEventNode implements CertificateUpdatedAuditEventType {
  public CertificateUpdatedAuditEventNode(UaNodeContext context, NodeId nodeId,
                                          QualifiedName browseName, LocalizedText displayName, LocalizedText description,
                                          UInteger writeMask, UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public CertificateUpdatedAuditEventNode(UaNodeContext context, NodeId nodeId,
                                          QualifiedName browseName, LocalizedText displayName, LocalizedText description,
                                          UInteger writeMask, UInteger userWriteMask, UByte eventNotifier) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
  }

  public PropertyNode getCertificateGroupNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(CertificateUpdatedAuditEventType.CERTIFICATE_GROUP);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public NodeId getCertificateGroup() {
    Optional<NodeId> propertyValue = getProperty(CertificateUpdatedAuditEventType.CERTIFICATE_GROUP);
    return propertyValue.orElse(null);
  }

  public void setCertificateGroup(NodeId value) {
    setProperty(CertificateUpdatedAuditEventType.CERTIFICATE_GROUP, value);
  }

  public PropertyNode getCertificateTypeNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(CertificateUpdatedAuditEventType.CERTIFICATE_TYPE);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public NodeId getCertificateType() {
    Optional<NodeId> propertyValue = getProperty(CertificateUpdatedAuditEventType.CERTIFICATE_TYPE);
    return propertyValue.orElse(null);
  }

  public void setCertificateType(NodeId value) {
    setProperty(CertificateUpdatedAuditEventType.CERTIFICATE_TYPE, value);
  }
}
