package cn.teaey.apns4j.protocol;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class InvalidErrorResponse extends RuntimeException {
    /**
     * <p>Constructor for InvalidErrorResponse.</p>
     *
     * @param msg a {@link String} object.
     */
    public InvalidErrorResponse(String msg) {
        super(msg);
    }
}
