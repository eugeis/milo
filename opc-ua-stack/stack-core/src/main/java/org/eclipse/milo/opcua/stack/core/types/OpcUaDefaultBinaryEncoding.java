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

package org.eclipse.milo.opcua.stack.core.types;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.eclipse.milo.opcua.stack.core.StatusCodes;
import org.eclipse.milo.opcua.stack.core.UaSerializationException;
import org.eclipse.milo.opcua.stack.core.serialization.EncodingLimits;
import org.eclipse.milo.opcua.stack.core.serialization.OpcUaBinaryStreamDecoder;
import org.eclipse.milo.opcua.stack.core.serialization.OpcUaBinaryStreamEncoder;
import org.eclipse.milo.opcua.stack.core.serialization.codecs.OpcUaBinaryDataTypeCodec;
import org.eclipse.milo.opcua.stack.core.types.builtin.ByteString;
import org.eclipse.milo.opcua.stack.core.types.builtin.ExtensionObject;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;

public class OpcUaDefaultBinaryEncoding implements DataTypeEncoding {

    public static final QualifiedName ENCODING_NAME =
        new QualifiedName(0, "Default Binary");

    public static OpcUaDefaultBinaryEncoding getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final OpcUaDefaultBinaryEncoding INSTANCE = new OpcUaDefaultBinaryEncoding();
    }

    private final ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;

    private OpcUaDefaultBinaryEncoding() {}

    @Override
    public QualifiedName getName() {
        return ENCODING_NAME;
    }

    @Override
    public ExtensionObject.BodyType getBodyType() {
        return ExtensionObject.BodyType.ByteString;
    }

    @Override
    public Object encode(
        Object decodedBody,
        NodeId encodingId,
        EncodingLimits encodingLimits,
        DataTypeManager dataTypeManager) {

        try {
            @SuppressWarnings("unchecked")
            OpcUaBinaryDataTypeCodec<Object> codec =
                (OpcUaBinaryDataTypeCodec<Object>) dataTypeManager.getBinaryCodec(encodingId);

            if (codec == null) {
                throw new UaSerializationException(
                    StatusCodes.Bad_EncodingError,
                    "no codec registered for encodingId=" + encodingId);
            }

            ByteBuf buffer = allocator.buffer();

            OpcUaBinaryStreamEncoder writer = new OpcUaBinaryStreamEncoder(buffer, encodingLimits);

            codec.encode(() -> dataTypeManager, decodedBody, writer);

            byte[] bs = new byte[buffer.readableBytes()];
            buffer.readBytes(bs);
            buffer.release();

            return ByteString.of(bs);
        } catch (ClassCastException e) {
            throw new UaSerializationException(StatusCodes.Bad_EncodingError, e);
        }
    }

    @Override
    public Object decode(
        Object encodedBody,
        NodeId encodingId,
        EncodingLimits encodingLimits,
        DataTypeManager dataTypeManager) {

        try {
            @SuppressWarnings("unchecked")
            OpcUaBinaryDataTypeCodec<Object> codec =
                (OpcUaBinaryDataTypeCodec<Object>) dataTypeManager.getBinaryCodec(encodingId);

            if (codec == null) {
                throw new UaSerializationException(
                    StatusCodes.Bad_DecodingError,
                    "no codec registered for encodingId=" + encodingId);
            }

            ByteString binaryBody = (ByteString) encodedBody;
            byte[] bs = binaryBody.bytesOrEmpty();

            ByteBuf buffer = Unpooled.wrappedBuffer(bs);

            OpcUaBinaryStreamDecoder reader = new OpcUaBinaryStreamDecoder(buffer, encodingLimits);

            return codec.decode(() -> dataTypeManager, reader);
        } catch (ClassCastException e) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError, e);
        }
    }

}
