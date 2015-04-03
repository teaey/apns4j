package cn.teaey.apns4j.keystore.exception;
/**
 * User: Teaey
 * Date: 13-8-29
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
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
