package org.eclipse.milo.opcua.sdk.server.model.nodes.variables;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.model.types.variables.YArrayItemType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.structured.AxisInformation;

public class YArrayItemNode extends ArrayItemNode implements YArrayItemType {
  public YArrayItemNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                        LocalizedText displayName, LocalizedText description, UInteger writeMask,
                        UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public YArrayItemNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                        LocalizedText displayName, LocalizedText description, UInteger writeMask,
                        UInteger userWriteMask, DataValue value, NodeId dataType, Integer valueRank,
                        UInteger[] arrayDimensions, UByte accessLevel, UByte userAccessLevel,
                        double minimumSamplingInterval, boolean historizing) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, value, dataType, valueRank, arrayDimensions, accessLevel, userAccessLevel, minimumSamplingInterval, historizing);
  }

  public PropertyNode getXAxisDefinitionNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(YArrayItemType.X_AXIS_DEFINITION);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public AxisInformation getXAxisDefinition() {
    Optional<AxisInformation> propertyValue = getProperty(YArrayItemType.X_AXIS_DEFINITION);
    return propertyValue.orElse(null);
  }

  public void setXAxisDefinition(AxisInformation value) {
    setProperty(YArrayItemType.X_AXIS_DEFINITION, value);
  }
}
