/*
 * Copyright (c) 2018 Kevin Herron
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *   http://www.eclipse.org/org/documents/edl-v10.html.
 */

package org.eclipse.milo.opcua.sdk.server.nodes.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.ExpandedNodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

class ReferenceTable {

    private final List<RefRow> references = new ArrayList<>();

    void addReference(
        BrowsePath sourcePath,
        NodeId referenceTypeId,
        ExpandedNodeId targetNodeId) {

        references.add(new RefRow(sourcePath, referenceTypeId, new RefTarget(targetNodeId)));
    }

    void addReference(
        BrowsePath sourcePath,
        NodeId referenceTypeId,
        BrowsePath targetPath) {

        references.add(new RefRow(sourcePath, referenceTypeId, new RefTarget(targetPath)));
    }

    List<RefRow> getReferences(BrowsePath sourcePath) {
        return references.stream().filter(t -> t.browsePath.equals(sourcePath)).collect(Collectors.toList());
    }

    static ReferenceTable merge(ReferenceTable table1, ReferenceTable table2) {
        ReferenceTable mergedTable = new ReferenceTable();

        mergedTable.references.addAll(table1.references);

        table2.references.forEach(row -> {
            BrowsePath browsePath = row.browsePath;
            NodeId referenceTypeId = row.nodeId;

            if (Identifiers.HasTypeDefinition.equals(referenceTypeId)) {
                // Don't merge a HasTypeDefinition reference if there's already one present
                // for a given BrowsePath.
                // This logic may need to be extended to include other Reference types for
                // which there should only be one.

                boolean hasTypeDefinitionReference = mergedTable.references.stream().anyMatch(
                    r ->
                        r.browsePath.equals(browsePath) &&
                            r.nodeId.equals(Identifiers.HasTypeDefinition)
                );

                if (!hasTypeDefinitionReference) {
                    mergedTable.references.add(row);
                }
            } else if (!mergedTable.references.contains(row)) {
                mergedTable.references.add(row);
            }
        });

        return mergedTable;
    }

    static class RefRow {
        final BrowsePath browsePath;
        final NodeId nodeId;
        final RefTarget target;

        RefRow(BrowsePath browsePath, NodeId nodeId, RefTarget target) {
            this.browsePath = browsePath;
            this.nodeId = nodeId;
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RefRow row = (RefRow) o;
            return Objects.equals(browsePath, row.browsePath) &&
                Objects.equals(nodeId, row.nodeId) &&
                Objects.equals(target, row.target);
        }

        @Override
        public int hashCode() {
            return Objects.hash(browsePath, nodeId, target);
        }
    }

    static class RefTarget {
        final ExpandedNodeId targetNodeId;
        final BrowsePath targetPath;

        RefTarget(ExpandedNodeId targetNodeId) {
            this.targetNodeId = targetNodeId;
            this.targetPath = null;
        }

        RefTarget(BrowsePath targetPath) {
            this.targetPath = targetPath;
            this.targetNodeId = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RefTarget target = (RefTarget) o;
            return Objects.equals(targetNodeId, target.targetNodeId) &&
                Objects.equals(targetPath, target.targetPath);
        }

        @Override
        public int hashCode() {
            return Objects.hash(targetNodeId, targetPath);
        }
    }

}
