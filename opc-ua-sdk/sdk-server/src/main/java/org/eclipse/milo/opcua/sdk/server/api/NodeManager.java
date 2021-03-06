/*
 * Copyright (c) 2017 Kevin Herron
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

package org.eclipse.milo.opcua.sdk.server.api;

import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;

import org.eclipse.milo.opcua.sdk.core.Reference;
import org.eclipse.milo.opcua.sdk.server.api.nodes.Node;
import org.eclipse.milo.opcua.stack.core.types.builtin.ExpandedNodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

public interface NodeManager<T extends Node> {

    /**
     * Add a {@link Node} to the {@link NodeManager}.
     *
     * @param node the {@link Node} to add.
     */
    void addNode(T node);

    /**
     * Check if a {@link Node} identified by {@link NodeId} exists in this {@link NodeManager}.
     *
     * @param nodeId the {@link NodeId} of the {@link Node} in question.
     * @return {@code true} if this {@link NodeManager} contains the {@link Node} identified by {@code nodeId}.
     */
    boolean containsNode(NodeId nodeId);

    /**
     * Check if a {@link Node} exists in this {@link NodeManager}.
     *
     * @param node the {@link Node} in question.
     * @return {@code true} if this {@link NodeManager} contains the {@link Node}.
     */
    default boolean containsNode(T node) {
        return containsNode(node.getNodeId());
    }

    /**
     * Get the {@link Node} identified by {@code nodeId}, or {@code null} if non exists.
     *
     * @param nodeId the {@link NodeId} identifying the {@link Node}.
     * @return the {@link Node} identified by {@code nodeId}, or {@code null} if non exists.
     */
    @Nullable
    default T get(NodeId nodeId) {
        return getNode(nodeId).orElse(null);
    }

    @Nullable
    default T get(ExpandedNodeId nodeId) {
        return getNode(nodeId).orElse(null);
    }

    /**
     * Get the {@link Node} identified by the provided {@link NodeId}, if it exists.
     *
     * @param nodeId the {@link NodeId} of the {@link Node}.
     * @return an {@link Optional} containing the {@link Node}, if present.
     */
    Optional<T> getNode(NodeId nodeId);

    /**
     * Get the {@link Node} identified by the provided {@link ExpandedNodeId}, if it exists.
     *
     * @param nodeId the {@link ExpandedNodeId} of the {@link Node}.
     * @return an {@link Optional} containing the {@link Node}, if present.
     */
    default Optional<T> getNode(ExpandedNodeId nodeId) {
        return nodeId.local().flatMap(this::getNode);
    }

    /**
     * Remove the {@link Node} identified by the provided {@link NodeId}, if it exists.
     *
     * @param nodeId the {@link NodeId} of the {@link Node}.
     * @return an {@link Optional} containing the {@link Node}, if removed.
     */
    Optional<T> removeNode(NodeId nodeId);

    /**
     * Remove the provided {@link Node} if it matches the entry in this map for its {@link NodeId}.
     *
     * @param node the {@link Node} to remove.
     * @return {@code true} if the node was removed.
     */
    default boolean removeNode(T node) {
        return removeNode(node.getNodeId()).isPresent();
    }

    /**
     * Add a {@link Reference} to this {@link NodeManager}.
     *
     * @param reference the {@link Reference} to add.
     */
    void addReference(Reference reference);

    /**
     * Remove a {@link Reference} from this {@link NodeManager}.
     *
     * @param reference the {@link Reference} to remove.
     */
    void removeReference(Reference reference);

    /**
     * Get all {@link Reference}s where {@code nodeId} is the source.
     *
     * @param nodeId the {@link NodeId} of the source node.
     * @return all {@link Reference}s where {@code nodeId} is the source.
     */
    Set<Reference> getReferences(NodeId nodeId);

}
