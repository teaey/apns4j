package cn.teaey.apns4j.protocol;
/**
 * User: Teaey
 * Date: 13-8-31
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class InvalidDeviceTokenException extends RuntimeException
{
    /**
     * <p>Constructor for InvalidDeviceTokenException.</p>
     */
    public InvalidDeviceTokenException()
    {
        super();
    }
    /**
     * <p>Constructor for InvalidDeviceTokenException.</p>
     *
     * @param msg a {@link String} object.
     */
    public InvalidDeviceTokenException(String msg)
    {
        super(msg);
    }
}
