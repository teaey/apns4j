package cn.teaey.apns4j.network;
import cn.teaey.apns4j.APNSHelper;
import cn.teaey.apns4j.protocol.ErrorResponse;
import cn.teaey.apns4j.protocol.NotifyPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Teaey
 * Date: 13-8-30
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class SecurityConnection implements Connection, PayloadSender<NotifyPayload>
{
    private static final Logger log               = LoggerFactory.getLogger(SecurityConnection.class);
    private static final int    DEFAULT_TRY_TIMES = 3;
    private final int                   id;
    private final SecuritySocketFactory socketFactory;
    private       SSLSocket             socket;
    private       InputStream           in;
    private       OutputStream          out;
    private       int                   tryTimes;
    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    /**
     * <p>newSecurityConnection.</p>
     *
     * @param securitySocketFactory a {@link cn.teaey.apns4j.network.SecuritySocketFactory} object.
     * @return a {@link cn.teaey.apns4j.network.SecurityConnection} object.
     */
    public static SecurityConnection newSecurityConnection(SecuritySocketFactory securitySocketFactory)
    {
        return new SecurityConnection(securitySocketFactory);
    }
    /**
     * <p>newSecurityConnection.</p>
     *
     * @param securitySocketFactory a {@link cn.teaey.apns4j.network.SecuritySocketFactory} object.
     * @param tryTimes a int.
     * @return a {@link cn.teaey.apns4j.network.SecurityConnection} object.
     */
    public static SecurityConnection newSecurityConnection(SecuritySocketFactory securitySocketFactory, int tryTimes)
    {
        return new SecurityConnection(securitySocketFactory, tryTimes);
    }
    private SecurityConnection(SecuritySocketFactory socketFactory)
    {
        this(socketFactory, DEFAULT_TRY_TIMES);
    }
    private SecurityConnection(SecuritySocketFactory socketFactory, int tryTimes)
    {
        this.socketFactory = socketFactory;
        this.tryTimes = tryTimes;
        this.id = COUNTER.incrementAndGet();
    }
    /**
     * <p>flush.</p>
     *
     * @throws java.io.IOException if any.
     */
    public void flush() throws IOException
    {
        out().flush();
    }
    /**
     * Send a payload with a devicetoken bytes(32 bytes)
     *
     * @param deviceTokenBytes an array of byte.
     * @param notifyPayload a {@link cn.teaey.apns4j.protocol.NotifyPayload} object.
     * @throws java.io.IOException if any.
     */
    public void sendAndFlush(byte[] deviceTokenBytes, NotifyPayload notifyPayload) throws IOException
    {
        APNSHelper.checkDeviceToken(deviceTokenBytes);
        String jsonString = notifyPayload.toJsonString();
        byte[] binaryData = APNSHelper.toRequestBytes(deviceTokenBytes, jsonString, notifyPayload.getIdentifier(), notifyPayload.getExpiry());
        for (int i = 1; i <= tryTimes; i++)
        {
            try
            {
                socket();
                out().write(binaryData);
                flush();
                log.debug("Success send payload : {} to device : {} try : {}", new Object[]{jsonString, deviceTokenBytes, i});
                break;
            } catch (IOException e)
            {
                close();
                log.error("Failed send payload : {} to device : {} try : {}", new Object[]{jsonString, deviceTokenBytes, e});
                if (i == tryTimes)
                    throw e;
            }
        }
    }
    /**
     * Send a payload with a devicetoken string(length 64)
     *
     * @param deviceTokenString a {@link String} object.
     * @param notifyPayload a {@link cn.teaey.apns4j.protocol.NotifyPayload} object.
     * @throws java.io.IOException if any.
     */
    public void sendAndFlush(String deviceTokenString, NotifyPayload notifyPayload) throws IOException
    {
        sendAndFlush(APNSHelper.toByteArray(deviceTokenString), notifyPayload);
    }
    /**
     * <p>readErrorResponse.</p>
     *
     * @return a {@link cn.teaey.apns4j.protocol.ErrorResponse} object.
     * @throws java.io.IOException if any.
     */
    public ErrorResponse readErrorResponse() throws IOException
    {
        byte[] data = new byte[6];
        recv(data);
        return new ErrorResponse(data);
    }
    /**
     * <p>close.</p>
     */
    public void close()
    {
        if (null != socket)
        {
            try
            {
                socket.close();
            } catch (IOException e)
            {
                log.error("Error while closing socket", e);
            } finally
            {
                in = null;
                out = null;
                socket = null;
            }
        }
    }
    /** {@inheritDoc} */
    @Override
    public void send(byte[] data) throws IOException
    {
        out().write(data);
    }
    /** {@inheritDoc} */
    @Override
    public int recv(byte[] data) throws IOException
    {
        return in().read(data);
    }
    /**
     * <p>socket.</p>
     *
     * @throws cn.teaey.apns4j.network.NetworkException if any.
     */
    protected void socket() throws NetworkException
    {
        try
        {
            if (null != socket && null != in && null != out && !socket.isClosed())
            {
                return;
            }
            else
            {
                close();
                this.socket = socketFactory.createSocket();
                this.in = socket.getInputStream();
                this.out = socket.getOutputStream();
            }
        } catch (Exception e)
        {
            throw new NetworkException(e);
        }
    }
    /**
     * <p>out.</p>
     *
     * @return a {@link java.io.OutputStream} object.
     */
    protected OutputStream out()
    {
        return this.out;
    }
    /**
     * <p>in.</p>
     *
     * @return a {@link java.io.InputStream} object.
     */
    protected InputStream in()
    {
        return this.in;
    }
    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return a int.
     */
    public int getId()
    {
        return id;
    }
}
