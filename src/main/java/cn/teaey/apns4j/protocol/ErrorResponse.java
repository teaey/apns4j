package cn.teaey.apns4j.protocol;
import java.nio.ByteBuffer;

/**
 * User: Teaey
 * Date: 13-8-31
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class ErrorResponse
{
    private final byte command;
    private final byte status;
    private final int  identifier;
    /**
     * <p>Constructor for ErrorResponse.</p>
     *
     * @param data an array of byte.
     */
    public ErrorResponse(byte[] data)
    {
        if (null == data || data.length != 6)
        {
            throw new InvalidErrorResponse("");
        }
        ByteBuffer wrapper = ByteBuffer.wrap(data);
        this.command = wrapper.get();
        this.status = wrapper.get();
        this.identifier = wrapper.getInt();
    }
    /**
     * <p>Constructor for ErrorResponse.</p>
     *
     * @param command a byte.
     * @param status a byte.
     * @param identifier a int.
     */
    public ErrorResponse(byte command, byte status, int identifier)
    {
        this.command = command;
        this.status = status;
        this.identifier = identifier;
    }
    /**
     * <p>Getter for the field <code>command</code>.</p>
     *
     * @return a byte.
     */
    public byte getCommand()
    {
        return command;
    }
    /**
     * <p>Getter for the field <code>status</code>.</p>
     *
     * @return a byte.
     */
    public byte getStatus()
    {
        return status;
    }
    /**
     * <p>Getter for the field <code>identifier</code>.</p>
     *
     * @return a int.
     */
    public int getIdentifier()
    {
        return identifier;
    }
}
