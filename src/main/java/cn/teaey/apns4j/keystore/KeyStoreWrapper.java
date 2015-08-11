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

package cn.teaey.apns4j.keystore;

import java.security.KeyStore;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class KeyStoreWrapper {
    private final KeyStore keyStore;
    private final String keyStorePassword;

    /**
     * <p>Constructor for KeyStoreWraper.</p>
     *
     * @param keyStore         a {@link java.security.KeyStore} object.
     * @param keyStorePassword a {@link String} object.
     */
    public KeyStoreWrapper(KeyStore keyStore, String keyStorePassword) {
        this.keyStore = keyStore;
        this.keyStorePassword = keyStorePassword;
    }

    /**
     * <p>Getter for the field <code>keyStore</code>.</p>
     *
     * @return a {@link java.security.KeyStore} object.
     */
    public KeyStore getKeyStore() {
        return keyStore;
    }

    /**
     * <p>Getter for the field <code>keyStorePassword</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getKeyStorePassword() {
        return keyStorePassword;
    }
}
