package org.eclipse.milo.opcua.sdk.server.model.nodes.objects;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.model.nodes.variables.PropertyNode;
import org.eclipse.milo.opcua.sdk.server.model.types.objects.NonTransparentRedundancyType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;

public class NonTransparentRedundancyNode extends ServerRedundancyNode implements NonTransparentRedundancyType {
  public NonTransparentRedundancyNode(UaNodeContext context, NodeId nodeId,
                                      QualifiedName browseName, LocalizedText displayName, LocalizedText description,
                                      UInteger writeMask, UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public NonTransparentRedundancyNode(UaNodeContext context, NodeId nodeId,
                                      QualifiedName browseName, LocalizedText displayName, LocalizedText description,
                                      UInteger writeMask, UInteger userWriteMask, UByte eventNotifier) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
  }

  public PropertyNode getServerUriArrayNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(NonTransparentRedundancyType.SERVER_URI_ARRAY);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public String[] getServerUriArray() {
    Optional<String[]> propertyValue = getProperty(NonTransparentRedundancyType.SERVER_URI_ARRAY);
    return propertyValue.orElse(null);
  }

  public void setServerUriArray(String[] value) {
    setProperty(NonTransparentRedundancyType.SERVER_URI_ARRAY, value);
  }
}
