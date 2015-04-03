package cn.teaey.apns4j.keystore.exception;
/**
 * User: Teaey
 * Date: 13-8-30
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class InvalidKeyStorePasswordException extends InvalidKeyStoreException
{
    /**
     * <p>Constructor for InvalidKeyStorePasswordException.</p>
     *
     * @param msg a {@link String} object.
     * @param e a {@link Exception} object.
     */
    public InvalidKeyStorePasswordException(String msg, Exception e)
    {
        super(msg, e);
    }
    /**
     * <p>Constructor for InvalidKeyStorePasswordException.</p>
     *
     * @param e a {@link Exception} object.
     */
    public InvalidKeyStorePasswordException(Exception e)
    {
        super(e);
    }
}
