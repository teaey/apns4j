package cn.teaey.apns4j.network;
/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
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
