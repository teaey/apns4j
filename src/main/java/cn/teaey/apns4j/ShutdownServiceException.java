package cn.teaey.apns4j;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class ShutdownServiceException extends RuntimeException {
    /**
     * <p>Constructor for ShutdownServiceException.</p>
     *
     * @param msg a {@link String} object.
     */
    public ShutdownServiceException(String msg) {
        super(msg);
    }
}
