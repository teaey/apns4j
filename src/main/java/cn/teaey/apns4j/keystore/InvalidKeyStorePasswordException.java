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

/**
 * @author teaey
 * @since 1.0.0
 */
public class InvalidKeyStorePasswordException extends InvalidKeyStoreException {
    /**
     * <p>Constructor for InvalidKeyStorePasswordException.</p>
     *
     * @param msg a {@link String} object.
     * @param e   a {@link Exception} object.
     */
    public InvalidKeyStorePasswordException(String msg, Exception e) {
        super(msg, e);
    }

    /**
     * <p>Constructor for InvalidKeyStorePasswordException.</p>
     *
     * @param e a {@link Exception} object.
     */
    public InvalidKeyStorePasswordException(Exception e) {
        super(e);
    }
}
