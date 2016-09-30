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

import java.io.Closeable;

/**
 * @author teaey
 * @since 1.0.0
 */
public interface Channel extends Closeable {
    /**
     * Writes <code>data.length</code> bytes from the specified byte array
     * to this connection
     *
     * @param data an array of byte.
     */
    void send(byte[] data);

    /**
     * Reads some number of bytes from the connection and stores them into
     * the buffer array <code>data</code>. The number of bytes actually read is
     * returned as an integer.
     *
     * @param data an array of byte.
     * @return a int.
     */
    int recv(byte[] data);
}
