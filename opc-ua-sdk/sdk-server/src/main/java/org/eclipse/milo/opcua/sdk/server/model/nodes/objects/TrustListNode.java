package org.eclipse.milo.opcua.sdk.server.model.nodes.objects;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.model.nodes.variables.PropertyNode;
import org.eclipse.milo.opcua.sdk.server.model.types.objects.TrustListType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;

public class TrustListNode extends FileNode implements TrustListType {
  public TrustListNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                       LocalizedText displayName, LocalizedText description, UInteger writeMask,
                       UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public TrustListNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                       LocalizedText displayName, LocalizedText description, UInteger writeMask,
                       UInteger userWriteMask, UByte eventNotifier) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
  }

  public PropertyNode getLastUpdateTimeNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(TrustListType.LAST_UPDATE_TIME);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public DateTime getLastUpdateTime() {
    Optional<DateTime> propertyValue = getProperty(TrustListType.LAST_UPDATE_TIME);
    return propertyValue.orElse(null);
  }

  public void setLastUpdateTime(DateTime value) {
    setProperty(TrustListType.LAST_UPDATE_TIME, value);
  }
}
