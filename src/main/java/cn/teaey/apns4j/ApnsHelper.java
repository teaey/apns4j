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

package cn.teaey.apns4j;

import cn.teaey.apns4j.protocol.ApnsPayload;
import cn.teaey.apns4j.protocol.InvalidDeviceTokenException;
import cn.teaey.apns4j.protocol.Protocal;

import java.nio.ByteBuffer;

/**
 * @author teaey
 * @since 1.0.0
 */
public class ApnsHelper {
    private static final int HEADER_LENGTH = 45;

    /**
     * <p>toRequestBytes.</p>
     *
     * @param deviceTokenBytes an array of byte.
     * @param apnsPayload      a {@link ApnsPayload} object.
     * @return an array of byte.
     */
    public static final byte[] toRequestBytes(byte[] deviceTokenBytes, ApnsPayload apnsPayload) {
        byte[] payloadBytes = apnsPayload.toJsonBytes();
        ByteBuffer buffer = ByteBuffer.allocate(HEADER_LENGTH + payloadBytes.length);
        buffer.put((byte) 1);
        buffer.putInt(apnsPayload.getIdentifier());
        buffer.putInt(apnsPayload.getExpiry());
        buffer.putShort((short) 32);
        buffer.put(deviceTokenBytes);
        buffer.putShort((short) payloadBytes.length);
        buffer.put(payloadBytes);
        return buffer.array();
    }

    /**
     * <p>toRequestBytes.</p>
     *
     * @param deviceTokenBytes  an array of byte.
     * @param payloadJsonString a {@link String} object.
     * @param identifier        a int.
     * @param expiry            a int.
     * @return an array of byte.
     */
    public static final byte[] toRequestBytes(byte[] deviceTokenBytes, String payloadJsonString, int identifier, int expiry) {
        byte[] payloadBytes = payloadJsonString.getBytes(Protocal.DEF_CHARSET);
        ByteBuffer buffer = ByteBuffer.allocate(HEADER_LENGTH + payloadBytes.length);
        buffer.put((byte) 1);
        buffer.putInt(identifier);
        buffer.putInt(expiry);
        buffer.putShort((short) 32);
        buffer.put(deviceTokenBytes);
        buffer.putShort((short) payloadBytes.length);
        buffer.put(payloadBytes);
        return buffer.array();
    }

    /**
     * <p>toHexString.</p>
     *
     * @param bytes an array of byte.
     * @return a {@link String} object.
     */
    public static final String toHexString(byte[] bytes) {
        StringBuilder ret = new StringBuilder(64);
        for (byte each : bytes) {
            String hexStr = Integer.toHexString(each >= 0 ? each : (256 + each));
            if (hexStr.length() == 1) {
                ret.append("0");
            }
            ret.append(hexStr);
        }
        return ret.toString();
    }

    /**
     * <p>toByteArray.</p>
     *
     * @param deviceToken a {@link String} object.
     * @return an array of byte.
     */
    public static final byte[] toByteArray(String deviceToken) {
        byte[] deviceTokenAsBytes = new byte[deviceToken.length() / 2];
        deviceToken = deviceToken.toUpperCase();
        int j = 0;
        try {
            for (int i = 0; i < deviceToken.length(); i += 2) {
                String t = deviceToken.substring(i, i + 2);
                deviceTokenAsBytes[j++] = (byte) Integer.parseInt(t, 16);
            }
        } catch (NumberFormatException e) {
            throw new InvalidDeviceTokenException(e.getMessage());
        }
        return deviceTokenAsBytes;
    }

    /**
     * <p>checkDeviceToken.</p>
     *
     * @param deviceToken a {@link Object} object.
     */
    public static final void checkDeviceToken(Object deviceToken) {
        if (deviceToken instanceof String && ((String) deviceToken).length() != 64) {
            throw new InvalidDeviceTokenException("device token string must [64] length not [" + ((String) deviceToken).length() + "]");
        }
        if (deviceToken instanceof byte[] && ((byte[]) deviceToken).length != 32) {
            throw new InvalidDeviceTokenException("device token bytes must [32] length not [" + ((byte[]) deviceToken).length + "]");
        }
    }

    public static final void checkNullThrowException(Object target, String name) {
        if (null == target) {
            throw new NullPointerException(name == null ? "" : name);
        }
    }
}
