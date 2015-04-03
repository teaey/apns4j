package cn.teaey.apns4j.network;
import java.io.IOException;

/**
 * User: Teaey
 * Date: 13-9-1
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class NetworkException extends IOException
{
    /**
     * <p>Constructor for NetworkException.</p>
     *
     * @param e a {@link Exception} object.
     */
    public NetworkException(Exception e)
    {
        super(e);
    }
    /**
     * <p>Constructor for NetworkException.</p>
     *
     * @param msg a {@link String} object.
     */
    public NetworkException(String msg)
    {
        super(msg);
    }
}
