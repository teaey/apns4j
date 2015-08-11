package cn.teaey.apns4j.network;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public interface PayloadSender<T> {
    /**
     * <p>sendAndFlush.</p>
     *
     * @param deviceTokenBytes an array of byte.
     * @param payload          a T object.
     */
    ApnsFuture sendAndFlush(byte[] deviceTokenBytes, T payload);

    /**
     * <p>sendAndFlush.</p>
     *
     * @param deviceTokenString a {@link String} object.
     * @param payload           a T object.
     */
    ApnsFuture sendAndFlush(String deviceTokenString, T payload);
}
