package cn.teaey.apns4j.keystore.exception;
/**
 * User: Teaey
 * Date: 13-8-30
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class InvalidKeyStoreTypeException extends InvalidKeyStoreException
{
    /**
     * <p>Constructor for InvalidKeyStoreTypeException.</p>
     *
     * @param msg a {@link String} object.
     * @param e a {@link Exception} object.
     */
    public InvalidKeyStoreTypeException(String msg, Exception e)
    {
        super(msg, e);
    }
    /**
     * <p>Constructor for InvalidKeyStoreTypeException.</p>
     *
     * @param e a {@link Exception} object.
     */
    public InvalidKeyStoreTypeException(Exception e)
    {
        super(e);
    }
}
