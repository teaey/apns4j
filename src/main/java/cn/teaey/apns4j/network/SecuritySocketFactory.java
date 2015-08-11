/*
 *
 *  * Copyright 2015 The Apns4j Project
 *  *
 *  * The Netty Project licenses this file to you under the Apache License,
 *  * version 2.0 (the "License"); you may not use this file except in compliance
 *  * with the License. You may obtain a copy of the License at:
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  * License for the specific language governing permissions and limitations
 *  * under the License.
 *
 */

package cn.teaey.apns4j.network;

import cn.teaey.apns4j.keystore.InvalidKeyStoreException;
import cn.teaey.apns4j.keystore.KeyStoreWrapper;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public final class SecuritySocketFactory {
    /* The algorithm used by KeyManagerFactory */
    private static final String ALGORITHM = ((Security.getProperty("ssl.KeyManagerFactory.algorithm") == null) ? "sunx509" : Security.getProperty("ssl.KeyManagerFactory.algorithm"));
    /* The protocol used to create the SSLSocket */
    private static final String PROTOCOL = "TLS";
    //    static
    //    {
    //        Security.addProvider(new BouncyCastleProvider());
    //    }
    private final String host;
    private final int port;
    private final KeyStore keyStore;
    private final String keyStorePassword;
    private final SSLSocketFactory sslSocketFactory;
    private TrustManager[] trustManagers = new TrustManager[]{new ServerTrustingTrustManager()};

    private SecuritySocketFactory(String host, int port, KeyStore keyStore, String keyStorePassword) throws InvalidKeyStoreException {
        this.host = host;
        this.port = port;
        this.keyStore = keyStore;
        this.keyStorePassword = keyStorePassword;
        this.sslSocketFactory = createSSLSocketFactoryWithTrustManagers(trustManagers);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * <p>createSSLSocketFactoryWithTrustManagers.</p>
     *
     * @param trustManagers an array of {@link javax.net.ssl.TrustManager} objects.
     * @return a {@link javax.net.ssl.SSLSocketFactory} object.
     * @throws InvalidKeyStoreException if any.
     */
    protected SSLSocketFactory createSSLSocketFactoryWithTrustManagers(TrustManager[] trustManagers) throws InvalidKeyStoreException {
        // Get a KeyManager and initialize it
        try {
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(ALGORITHM);
            kmf.init(keyStore, keyStorePassword.toCharArray());
            // Get the SSLContext to help create SSLSocketFactory
            SSLContext sslc = SSLContext.getInstance(PROTOCOL);
            sslc.init(kmf.getKeyManagers(), trustManagers, null);
            return sslc.getSocketFactory();
        } catch (Exception e) {
            throw new InvalidKeyStoreException(e);
        }
    }

    /**
     * Create a SSLSocket which will be used to send data to Apple
     *
     * @return the SSLSocket
     */
    public SSLSocket createSocket() throws IOException {
        return (SSLSocket) sslSocketFactory.createSocket(host, port);
    }

    public static final class Builder {
        private String host;
        private int port;
        private KeyStore keyStore;
        private String keyStorePassword;

        private Builder() {
        }

        public SecuritySocketFactory.Builder keyStoreWrapper(KeyStoreWrapper keyStoreWrapper) {
            this.keyStore = keyStoreWrapper.getKeyStore();
            this.keyStorePassword = keyStoreWrapper.getKeyStorePassword();
            return this;
        }

        public SecuritySocketFactory.Builder appleServer(AppleServer appleServer) {
            this.host = appleServer.getHost();
            this.port = appleServer.getPort();
            return this;
        }

        public SecuritySocketFactory.Builder host(String host) {
            this.host = host;
            return this;
        }

        public SecuritySocketFactory.Builder port(int port) {
            this.port = port;
            return this;
        }

        public SecuritySocketFactory.Builder keyStore(KeyStore keyStore) {
            this.keyStore = keyStore;
            return this;
        }

        public SecuritySocketFactory.Builder keyStorePassword(String keyStorePassword) {
            if (null == keyStorePassword) {
                keyStorePassword = "";
            }
            this.keyStorePassword = keyStorePassword;
            return this;
        }

        public SecuritySocketFactory build() throws InvalidKeyStoreException {
            if (host == null) {
                throw new NullPointerException("host");
            }
            if (keyStore == null) {
                throw new NullPointerException("keystore");
            }
            if (keyStorePassword == null) {
                throw new NullPointerException("keyStorePassword");
            }
            return new SecuritySocketFactory(host, port, keyStore, keyStorePassword);
        }
    }

    private class ServerTrustingTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            throw new CertificateException("not trusted");
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            // trust all servers
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
