package cn.teaey.apns4j.network;
import java.io.IOException;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
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
