package org.eclipse.milo.opcua.sdk.server.model.nodes.objects;

import org.eclipse.milo.opcua.sdk.server.model.types.objects.FileDirectoryType;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;

public class FileDirectoryNode extends FolderNode implements FileDirectoryType {
  public FileDirectoryNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                           LocalizedText displayName, LocalizedText description, UInteger writeMask,
                           UInteger userWriteMask) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask);
  }

  public FileDirectoryNode(UaNodeContext context, NodeId nodeId, QualifiedName browseName,
                           LocalizedText displayName, LocalizedText description, UInteger writeMask,
                           UInteger userWriteMask, UByte eventNotifier) {
    super(context, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
  }
}
