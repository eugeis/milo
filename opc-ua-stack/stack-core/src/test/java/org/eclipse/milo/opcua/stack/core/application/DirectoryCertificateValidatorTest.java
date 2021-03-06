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

package org.eclipse.milo.opcua.stack.core.application;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class DirectoryCertificateValidatorTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testPruneRejectedCertificateDir() throws Exception {
        File securityTestDir = new File(System.getProperty("java.io.tmpdir"), "securityTest");
        if (!securityTestDir.exists() && !securityTestDir.mkdirs()) {
            throw new Exception("unable to create security temp dir: " + securityTestDir);
        }
        logger.info("using {}", securityTestDir);

        DirectoryCertificateValidator validator = new DirectoryCertificateValidator(securityTestDir);

        File rejectedDir = validator.getRejectedDir();

        for (int i = 0; i < DirectoryCertificateValidator.MAX_REJECTED_CERTIFICATES; i++) {
            File tmp = File.createTempFile("foo", "bar", rejectedDir);
            tmp.deleteOnExit();
        }

        File[] rejectedFiles = rejectedDir.listFiles();
        assertNotNull(rejectedFiles);
        assertEquals(rejectedFiles.length, DirectoryCertificateValidator.MAX_REJECTED_CERTIFICATES);

        validator.pruneOldRejectedCertificates();
        rejectedFiles = rejectedDir.listFiles();
        assertNotNull(rejectedFiles);
        assertEquals(rejectedFiles.length, DirectoryCertificateValidator.MAX_REJECTED_CERTIFICATES - 1);
    }

}
