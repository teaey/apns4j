package cn.teaey.apns4j;
import cn.teaey.apns4j.protocol.InvalidDeviceTokenException;
import cn.teaey.apns4j.protocol.NotifyPayload;
import cn.teaey.apns4j.protocol.Protocal;

import java.nio.ByteBuffer;

/**
 * User: Teaey
 * Date: 13-8-29
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class APNSHelper
{
    private static final int    HEADER_LENGTH = 45;
    /**
     * <p>toRequestBytes.</p>
     *
     * @param deviceTokenBytes an array of byte.
     * @param notifyPayload a {@link cn.teaey.apns4j.protocol.NotifyPayload} object.
     * @return an array of byte.
     */
    public static final byte[] toRequestBytes(byte[] deviceTokenBytes, NotifyPayload notifyPayload)
    {
        byte[] payloadBytes = notifyPayload.toJsonBytes();
        ByteBuffer buffer = ByteBuffer.allocate(HEADER_LENGTH + payloadBytes.length);
        buffer.put((byte) 1);
        buffer.putInt(notifyPayload.getIdentifier());
        buffer.putInt(notifyPayload.getExpiry());
        buffer.putShort((short) 32);
        buffer.put(deviceTokenBytes);
        buffer.putShort((short) payloadBytes.length);
        buffer.put(payloadBytes);
        return buffer.array();
    }
    /**
     * <p>toRequestBytes.</p>
     *
     * @param deviceTokenBytes an array of byte.
     * @param payloadJsonString a {@link String} object.
     * @param identifier a int.
     * @param expiry a int.
     * @return an array of byte.
     */
    public static final byte[] toRequestBytes(byte[] deviceTokenBytes, String payloadJsonString, int identifier, int expiry)
    {
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
    public static final String toHexString(byte[] bytes)
    {
        StringBuilder ret = new StringBuilder(64);
        for (byte each : bytes)
        {
            String hexStr = Integer.toHexString(each >= 0 ? each : (256 + each));
            if (hexStr.length() == 1)
            {
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
    public static final byte[] toByteArray(String deviceToken)
    {
        byte[] deviceTokenAsBytes = new byte[deviceToken.length() / 2];
        deviceToken = deviceToken.toUpperCase();
        int j = 0;
        try
        {
            for (int i = 0; i < deviceToken.length(); i += 2)
            {
                String t = deviceToken.substring(i, i + 2);
                deviceTokenAsBytes[j++] = (byte) Integer.parseInt(t, 16);
            }
        } catch (NumberFormatException e)
        {
            throw new InvalidDeviceTokenException(e.getMessage());
        }
        return deviceTokenAsBytes;
    }
    /**
     * <p>checkDeviceToken.</p>
     *
     * @param deviceToken a {@link Object} object.
     */
    public static final void checkDeviceToken(Object deviceToken)
    {
        if (deviceToken instanceof String && ((String) deviceToken).length() != 64)
        {
            throw new InvalidDeviceTokenException("device token string must [64] length not [" + ((String) deviceToken).length() + "]");
        }
        if (deviceToken instanceof byte[] && ((byte[]) deviceToken).length != 32)
        {
            throw new InvalidDeviceTokenException("device token bytes must [32] length not [" + ((byte[]) deviceToken).length + "]");
        }
    }
}
