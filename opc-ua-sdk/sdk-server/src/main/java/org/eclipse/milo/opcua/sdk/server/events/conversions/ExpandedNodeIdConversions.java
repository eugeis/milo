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

package org.eclipse.milo.opcua.sdk.server.events.conversions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.eclipse.milo.opcua.stack.core.BuiltinDataType;
import org.eclipse.milo.opcua.stack.core.types.builtin.ExpandedNodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

final class ExpandedNodeIdConversions {

    private ExpandedNodeIdConversions() {}

    @Nullable
    static NodeId expandedNodeIdToNodeId(@Nonnull ExpandedNodeId e) {
        return e.local().orElse(null);
    }

    @Nonnull
    static String expandedNodeIdToString(@Nonnull ExpandedNodeId e) {
        return e.toParseableString();
    }

    @Nullable
    static Object convert(@Nonnull Object o, BuiltinDataType targetType, boolean implicit) {
        if (o instanceof ExpandedNodeId) {
            ExpandedNodeId eni = (ExpandedNodeId) o;

            return implicit ?
                implicitConversion(eni, targetType) :
                explicitConversion(eni, targetType);
        } else {
            return null;
        }
    }

    @Nullable
    static Object explicitConversion(@Nonnull ExpandedNodeId eni, BuiltinDataType targetType) {
        //@formatter:off
        switch (targetType) {
            case NodeId:    return expandedNodeIdToNodeId(eni);
            default:        return implicitConversion(eni, targetType);
        }
        //@formatter:on
    }

    @Nullable
    static Object implicitConversion(@Nonnull ExpandedNodeId eni, BuiltinDataType targetType) {
        //@formatter:off
        switch (targetType) {
            case String:    return expandedNodeIdToString(eni);
            default:        return null;
        }
        //@formatter:on
    }

}
