package cn.teaey.apns4j.keystore;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class InvalidKeyStoreTypeException extends InvalidKeyStoreException {
    /**
     * <p>Constructor for InvalidKeyStoreTypeException.</p>
     *
     * @param msg a {@link String} object.
     * @param e   a {@link Exception} object.
     */
    public InvalidKeyStoreTypeException(String msg, Exception e) {
        super(msg, e);
    }

    /**
     * <p>Constructor for InvalidKeyStoreTypeException.</p>
     *
     * @param e a {@link Exception} object.
     */
    public InvalidKeyStoreTypeException(Exception e) {
        super(e);
    }
}
