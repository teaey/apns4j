package cn.teaey.apns4j.network;
/**
 * User: Teaey
 * Date: 13-8-30
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class ConnectionException extends Exception
{
    /**
     * <p>Constructor for ConnectionException.</p>
     *
     * @param e a {@link Exception} object.
     */
    public ConnectionException(Exception e)
    {
        super(e);
    }
}
