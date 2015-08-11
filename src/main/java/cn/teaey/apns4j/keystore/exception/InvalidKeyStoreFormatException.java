package cn.teaey.apns4j.keystore.exception;
/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class InvalidKeyStoreFormatException extends InvalidKeyStoreException
{
    /**
     * <p>Constructor for InvalidKeyStoreFormatException.</p>
     *
     * @param message a {@link String} object.
     */
    public InvalidKeyStoreFormatException(String message)
    {
        super(message);
    }
    /**
     * <p>Constructor for InvalidKeyStoreFormatException.</p>
     *
     * @param message a {@link String} object.
     * @param cause a {@link Exception} object.
     */
    public InvalidKeyStoreFormatException(String message, Exception cause)
    {
        super(message, cause);
    }
    /**
     * <p>Constructor for InvalidKeyStoreFormatException.</p>
     *
     * @param cause a {@link Exception} object.
     */
    public InvalidKeyStoreFormatException(Exception cause)
    {
        super(cause);
    }
}
