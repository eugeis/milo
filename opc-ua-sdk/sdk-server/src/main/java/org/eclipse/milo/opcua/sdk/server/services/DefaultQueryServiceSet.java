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

package org.eclipse.milo.opcua.sdk.server.services;

import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.server.services.QueryServiceSet;
import org.eclipse.milo.opcua.stack.server.services.ServiceRequest;

import static org.eclipse.milo.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

public class DefaultQueryServiceSet implements QueryServiceSet {

    private final ServiceCounter queryFirstMetric = new ServiceCounter();
    private final ServiceCounter queryNextMetric = new ServiceCounter();

    @Override
    public void onQueryFirst(ServiceRequest service) throws UaException {
        queryFirstMetric.record(service);

        service.setServiceFault(Bad_ServiceUnsupported);
    }

    @Override
    public void onQueryNext(ServiceRequest service) throws UaException {
        queryNextMetric.record(service);

        service.setServiceFault(Bad_ServiceUnsupported);
    }

}
