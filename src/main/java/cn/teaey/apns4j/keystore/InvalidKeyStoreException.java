package cn.teaey.apns4j.keystore;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class InvalidKeyStoreException extends Exception {
    /**
     * <p>Constructor for InvalidKeyStoreException.</p>
     *
     * @param message a {@link String} object.
     */
    public InvalidKeyStoreException(String message) {
        super(message);
    }

    /**
     * <p>Constructor for InvalidKeyStoreException.</p>
     *
     * @param message a {@link String} object.
     * @param cause   a {@link Exception} object.
     */
    public InvalidKeyStoreException(String message, Exception cause) {
        super(message, cause);
    }

    /**
     * <p>Constructor for InvalidKeyStoreException.</p>
     *
     * @param cause a {@link Exception} object.
     */
    public InvalidKeyStoreException(Exception cause) {
        super(cause);
    }
}
