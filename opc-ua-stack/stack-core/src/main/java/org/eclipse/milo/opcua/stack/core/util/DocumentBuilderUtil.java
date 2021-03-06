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

package org.eclipse.milo.opcua.stack.core.util;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.LoggerFactory;

public class DocumentBuilderUtil {

    private DocumentBuilderUtil() {}

    /**
     * A shared {@link DocumentBuilderFactory} that has been configured securely to prevent XXE attacks.
     */
    public static final DocumentBuilderFactory SHARED_FACTORY = DocumentBuilderFactory.newInstance();

    static {
        SHARED_FACTORY.setCoalescing(true);
        SHARED_FACTORY.setNamespaceAware(true);

        // XXE Prevention
        // https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Prevention_Cheat_Sheet
        SHARED_FACTORY.setExpandEntityReferences(false);
        SHARED_FACTORY.setXIncludeAware(false);

        trySetFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        trySetFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        trySetFeature("http://xml.org/sax/features/external-general-entities", false);
        trySetFeature("http://xml.org/sax/features/external-parameter-entities", false);
        trySetFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    }

    private static void trySetFeature(String feature, boolean value) {
        try {
            SHARED_FACTORY.setFeature(feature, value);
        } catch (Exception e) {
            LoggerFactory.getLogger(DocumentBuilderUtil.class)
                .debug("Error configuring feature: " + feature, e);
        }
    }

}
