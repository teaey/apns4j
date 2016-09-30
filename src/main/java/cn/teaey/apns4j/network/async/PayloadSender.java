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

package cn.teaey.apns4j.network.async;

/**
 * @author teaey
 * @since 1.0.0
 */
public interface PayloadSender<T> {
    /**
     * <p>send.</p>
     *
     * @param deviceTokenBytes an array of byte.
     * @param payload          a T object.
     * @return feture
     */
    ApnsFuture send(byte[] deviceTokenBytes, T payload);

    /**
     * <p>send.</p>
     *
     * @param deviceTokenString a {@link String} object.
     * @param payload           a T object.
     * @return feture
     */
    ApnsFuture send(String deviceTokenString, T payload);
}
