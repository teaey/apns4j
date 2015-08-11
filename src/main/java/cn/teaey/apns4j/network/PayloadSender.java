package cn.teaey.apns4j.network;
import java.io.IOException;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public interface PayloadSender<T>
{
    /**
     * <p>sendAndFlush.</p>
     *
     * @param deviceTokenBytes an array of byte.
     * @param payload a T object.
     * @throws java.io.IOException if any.
     */
    void sendAndFlush(byte[] deviceTokenBytes, T payload) throws IOException;
    /**
     * <p>sendAndFlush.</p>
     *
     * @param deviceTokenString a {@link String} object.
     * @param payload a T object.
     * @throws java.io.IOException if any.
     */
    public void sendAndFlush(String deviceTokenString, T payload) throws IOException;
}
