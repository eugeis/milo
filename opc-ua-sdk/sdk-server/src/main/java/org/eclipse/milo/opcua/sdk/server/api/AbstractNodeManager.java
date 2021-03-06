/*
 * Copyright (c) 2016 Kevin Herron
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

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Multimaps;
import org.eclipse.milo.opcua.sdk.core.Reference;
import org.eclipse.milo.opcua.sdk.server.api.nodes.Node;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

public abstract class AbstractNodeManager<T extends Node> implements NodeManager<T> {

    private final ConcurrentMap<NodeId, T> nodeMap;
    private final ListMultimap<NodeId, Reference> references;

    public AbstractNodeManager() {
        nodeMap = makeNodeMap(new MapMaker());
        references = Multimaps.synchronizedListMultimap(ArrayListMultimap.create());
    }

    /**
     * Optionally customize the backing {@link ConcurrentMap} with the provided {@link MapMaker}.
     *
     * @param mapMaker the {@link MapMaker} that make the backing map with.
     * @return a {@link ConcurrentMap}.
     */
    protected ConcurrentMap<NodeId, T> makeNodeMap(MapMaker mapMaker) {
        return mapMaker.makeMap();
    }

    @Override
    public void addNode(T node) {
        nodeMap.put(node.getNodeId(), node);
    }

    @Override
    public boolean containsNode(NodeId nodeId) {
        return nodeMap.containsKey(nodeId);
    }

    @Override
    public Optional<T> getNode(NodeId nodeId) {
        return Optional.ofNullable(nodeMap.get(nodeId));
    }

    @Override
    public Optional<T> removeNode(NodeId nodeId) {
        return Optional.ofNullable(nodeMap.remove(nodeId));
    }

    @Override
    public void addReference(Reference reference) {
        references.put(reference.getSourceNodeId(), reference);
    }

    @Override
    public void removeReference(Reference reference) {
        references.remove(reference.getSourceNodeId(), reference);
    }

    @Override
    public Set<Reference> getReferences(NodeId nodeId) {
        return new LinkedHashSet<>(references.get(nodeId));
    }

}
