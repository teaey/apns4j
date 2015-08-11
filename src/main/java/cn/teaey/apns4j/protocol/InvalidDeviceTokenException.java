package cn.teaey.apns4j.protocol;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class InvalidDeviceTokenException extends RuntimeException {
    /**
     * <p>Constructor for InvalidDeviceTokenException.</p>
     */
    public InvalidDeviceTokenException() {
        super();
    }

    /**
     * <p>Constructor for InvalidDeviceTokenException.</p>
     *
     * @param msg a {@link String} object.
     */
    public InvalidDeviceTokenException(String msg) {
        super(msg);
    }
}
