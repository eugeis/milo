package org.eclipse.milo.opcua.sdk.server.model.nodes.objects;

import java.util.Optional;

import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.model.nodes.variables.PropertyNode;
import org.eclipse.milo.opcua.sdk.server.model.types.objects.ModellingRuleType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NamingRuleType;

public class ModellingRuleNode extends BaseObjectNode implements ModellingRuleType {
  public ModellingRuleNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                           LocalizedText displayName, LocalizedText description, UInteger writeMask,
                           UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public ModellingRuleNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                           LocalizedText displayName, LocalizedText description, UInteger writeMask,
                           UInteger userWriteMask, UByte eventNotifier) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
  }

  public PropertyNode getNamingRuleNode() {
    Optional<VariableNode> propertyNode = getPropertyNode(ModellingRuleType.NAMING_RULE);
    return (PropertyNode) propertyNode.orElse(null);
  }

  public NamingRuleType getNamingRule() {
    Optional<NamingRuleType> propertyValue = getProperty(ModellingRuleType.NAMING_RULE);
    return propertyValue.orElse(null);
  }

  public void setNamingRule(NamingRuleType value) {
    setProperty(ModellingRuleType.NAMING_RULE, value);
  }
}
