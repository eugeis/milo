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

package org.eclipse.milo.opcua.stack.server.services;

import org.eclipse.milo.opcua.stack.core.StatusCodes;
import org.eclipse.milo.opcua.stack.core.UaException;

public interface SubscriptionServiceSet {

    default void onCreateSubscription(ServiceRequest serviceRequest) throws UaException {
        serviceRequest.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

    default void onModifySubscription(ServiceRequest serviceRequest) throws UaException {
        serviceRequest.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

    default void onDeleteSubscriptions(ServiceRequest serviceRequest) throws UaException {
        serviceRequest.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

    default void onTransferSubscriptions(ServiceRequest serviceRequest) throws UaException {
        serviceRequest.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

    default void onSetPublishingMode(ServiceRequest serviceRequest) throws UaException {
        serviceRequest.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

    default void onPublish(ServiceRequest serviceRequest) throws UaException {
        serviceRequest.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

    default void onRepublish(ServiceRequest serviceRequest) throws UaException {
        serviceRequest.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

}
