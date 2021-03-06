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

package org.eclipse.milo.opcua.stack.server;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.eclipse.milo.opcua.stack.core.Stack;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.transport.TransportProfile;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MessageSecurityMode;
import org.eclipse.milo.opcua.stack.core.types.structured.UserTokenPolicy;

public class EndpointConfiguration {

    private final TransportProfile transportProfile;
    private final String bindAddress;
    private final int bindPort;
    private final String hostname;
    private final String path;
    private final X509Certificate certificate;
    private final SecurityPolicy securityPolicy;
    private final MessageSecurityMode securityMode;
    private final ImmutableList<UserTokenPolicy> tokenPolicies;

    private EndpointConfiguration(
        TransportProfile transportProfile,
        String bindAddress,
        int bindPort,
        String hostname,
        String path,
        @Nullable X509Certificate certificate,
        SecurityPolicy securityPolicy,
        MessageSecurityMode securityMode,
        List<UserTokenPolicy> tokenPolicies) {

        this.transportProfile = transportProfile;
        this.bindAddress = bindAddress;
        this.bindPort = bindPort;
        this.hostname = hostname;
        this.path = path;
        this.certificate = certificate;
        this.securityPolicy = securityPolicy;
        this.securityMode = securityMode;
        this.tokenPolicies = ImmutableList.copyOf(tokenPolicies);
    }

    public TransportProfile getTransportProfile() {
        return transportProfile;
    }

    public String getBindAddress() {
        return bindAddress;
    }

    public int getBindPort() {
        return bindPort;
    }

    public String getHostname() {
        return hostname;
    }

    public String getPath() {
        return path;
    }

    @Nullable
    public X509Certificate getCertificate() {
        return certificate;
    }

    public SecurityPolicy getSecurityPolicy() {
        return securityPolicy;
    }

    public MessageSecurityMode getSecurityMode() {
        return securityMode;
    }

    public ImmutableList<UserTokenPolicy> getTokenPolicies() {
        return tokenPolicies;
    }

    public String getEndpointUrl() {
        String scheme = transportProfile.getScheme();
        String p = path.isEmpty() || path.startsWith("/") ? path : "/" + path;

        return String.format("%s://%s:%s%s", scheme, hostname, bindPort, p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndpointConfiguration that = (EndpointConfiguration) o;
        return bindPort == that.bindPort &&
            transportProfile == that.transportProfile &&
            Objects.equal(bindAddress, that.bindAddress) &&
            Objects.equal(hostname, that.hostname) &&
            Objects.equal(path, that.path) &&
            Objects.equal(certificate, that.certificate) &&
            securityPolicy == that.securityPolicy &&
            securityMode == that.securityMode &&
            Objects.equal(tokenPolicies, that.tokenPolicies);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
            transportProfile,
            bindAddress,
            bindPort,
            hostname,
            path,
            certificate,
            securityPolicy,
            securityMode,
            tokenPolicies
        );
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("transportProfile", transportProfile)
            .add("bindAddress", bindAddress)
            .add("bindPort", bindPort)
            .add("hostname", hostname)
            .add("path", path)
            .add("certificate", certificate)
            .add("securityPolicy", securityPolicy)
            .add("securityMode", securityMode)
            .add("tokenPolicies", tokenPolicies)
            .toString();
    }

    public static EndpointConfiguration.Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        TransportProfile transportProfile = TransportProfile.TCP_UASC_UABINARY;
        String bindAddress = "localhost";
        int bindPort = Stack.DEFAULT_TCP_PORT;
        String hostname = "localhost";
        String path = "";
        X509Certificate certificate = null;
        SecurityPolicy securityPolicy = SecurityPolicy.None;
        MessageSecurityMode securityMode = MessageSecurityMode.None;
        List<UserTokenPolicy> tokenPolicies = new ArrayList<>();

        public Builder setTransportProfile(TransportProfile transportProfile) {
            this.transportProfile = transportProfile;
            return this;
        }

        public Builder setBindAddress(String bindAddress) {
            this.bindAddress = bindAddress;
            return this;
        }

        public Builder setBindPort(int bindPort) {
            this.bindPort = bindPort;
            return this;
        }

        public Builder setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setCertificate(@Nullable X509Certificate certificate) {
            this.certificate = certificate;
            return this;
        }

        public Builder setSecurityPolicy(SecurityPolicy securityPolicy) {
            this.securityPolicy = securityPolicy;
            return this;
        }

        public Builder setSecurityMode(MessageSecurityMode securityMode) {
            this.securityMode = securityMode;
            return this;
        }

        public Builder addTokenPolicy(UserTokenPolicy tokenPolicy) {
            tokenPolicies.add(tokenPolicy);
            return this;
        }

        public Builder addTokenPolicies(UserTokenPolicy... tokenPolicies) {
            Collections.addAll(this.tokenPolicies, tokenPolicies);
            return this;
        }

        private Builder addTokenPolicies(List<UserTokenPolicy> tokenPolicies) {
            this.tokenPolicies.addAll(tokenPolicies);
            return this;
        }

        public Builder copy() {
            return new Builder()
                .setTransportProfile(transportProfile)
                .setBindAddress(bindAddress)
                .setBindPort(bindPort)
                .setHostname(hostname)
                .setPath(path)
                .setCertificate(certificate)
                .setSecurityPolicy(securityPolicy)
                .setSecurityMode(securityMode)
                .addTokenPolicies(tokenPolicies);
        }

        public EndpointConfiguration build() {
            if (securityPolicy != SecurityPolicy.None ||
                securityMode != MessageSecurityMode.None) {

                if (securityPolicy == SecurityPolicy.None) {
                    throw new IllegalArgumentException("securityPolicy: " + securityPolicy);
                }
                if (securityMode == MessageSecurityMode.None) {
                    throw new IllegalArgumentException("securityMode: " + securityMode);
                }
                if (certificate == null) {
                    throw new IllegalStateException("security requires certificate");
                }
            }

            if (tokenPolicies.isEmpty()) {
                throw new IllegalStateException("no UserTokenPolicy");
            }

            switch (transportProfile) {
                case HTTPS_UAXML:
                case HTTPS_UAJSON:
                case WSS_UASC_UABINARY:
                case WSS_UAJSON:
                    throw new IllegalArgumentException(
                        "unsupported transport: " + transportProfile);

                default:
                    break;
            }

            return new EndpointConfiguration(
                transportProfile,
                bindAddress,
                bindPort,
                hostname,
                path,
                certificate,
                securityPolicy,
                securityMode,
                tokenPolicies
            );
        }

    }
}
