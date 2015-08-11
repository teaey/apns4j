package cn.teaey.apns4j.network;

import cn.teaey.apns4j.APNSHelper;
import cn.teaey.apns4j.protocol.ErrorResponse;
import cn.teaey.apns4j.protocol.NotifyPayload;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class SecurityConnection implements Connection, PayloadSender<NotifyPayload> {
    //private static final Logger log               = LoggerFactory.getLogger(SecurityConnection.class);
    public static final int DEFAULT_TRY_TIMES = 3;
    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    private final int id;
    private final SecuritySocketFactory socketFactory;
    private SSLSocket socket;
    private InputStream in;
    private OutputStream out;
    private int tryTimes;

    private SecurityConnection(SecuritySocketFactory socketFactory) {
        this(socketFactory, DEFAULT_TRY_TIMES);
    }

    private SecurityConnection(SecuritySocketFactory socketFactory, int tryTimes) {
        this.socketFactory = socketFactory;
        this.tryTimes = tryTimes;
        this.id = COUNTER.incrementAndGet();
    }

    /**
     * <p>newSecurityConnection.</p>
     *
     * @param securitySocketFactory a {@link cn.teaey.apns4j.network.SecuritySocketFactory} object.
     * @return a {@link cn.teaey.apns4j.network.SecurityConnection} object.
     */
    public static SecurityConnection newSecurityConnection(SecuritySocketFactory securitySocketFactory) {
        return new SecurityConnection(securitySocketFactory);
    }

    /**
     * <p>newSecurityConnection.</p>
     *
     * @param securitySocketFactory a {@link cn.teaey.apns4j.network.SecuritySocketFactory} object.
     * @param tryTimes              a int.
     * @return a {@link cn.teaey.apns4j.network.SecurityConnection} object.
     */
    public static SecurityConnection newSecurityConnection(SecuritySocketFactory securitySocketFactory, int tryTimes) {
        return new SecurityConnection(securitySocketFactory, tryTimes);
    }

    /**
     * <p>flush.</p>
     *
     * @throws java.io.IOException if any.
     */
    private void flush() throws IOException {
        out().flush();
    }

    /**
     * Send a payload with a devicetoken bytes(32 bytes)
     *
     * @param deviceTokenBytes an array of byte.
     * @param notifyPayload    a {@link cn.teaey.apns4j.protocol.NotifyPayload} object.
     */
    public ApnsFuture sendAndFlush(byte[] deviceTokenBytes, NotifyPayload notifyPayload) {
        APNSHelper.checkDeviceToken(deviceTokenBytes);
        String jsonString = notifyPayload.toJsonString();
        byte[] binaryData = APNSHelper.toRequestBytes(deviceTokenBytes, jsonString, notifyPayload.getIdentifier(), notifyPayload.getExpiry());
        for (int i = 1; i <= tryTimes; i++) {
            try {
                socket();
                out().write(binaryData);
                flush();
                //log.debug("Success send payload : {} to device : {} try : {}", new Object[]{jsonString, deviceTokenBytes, i});
                break;
            } catch (IOException e) {
                try {
                    _close();
                } catch (IOException e1) {
                }
                //log.error("Failed send payload : {} to device : {} try : {}", new Object[]{jsonString, deviceTokenBytes, e});
                if (i == tryTimes)
                    throw new ApnsException(e);
            }
        }
        return null;
    }

    /**
     * Send a payload with a devicetoken string(length 64)
     *
     * @param deviceTokenString a {@link String} object.
     * @param notifyPayload     a {@link cn.teaey.apns4j.protocol.NotifyPayload} object.
     * @throws java.io.IOException if any.
     */
    public ApnsFuture sendAndFlush(String deviceTokenString, NotifyPayload notifyPayload) {
        return sendAndFlush(APNSHelper.toByteArray(deviceTokenString), notifyPayload);
    }

    /**
     * <p>readErrorResponse.</p>
     *
     * @return a {@link cn.teaey.apns4j.protocol.ErrorResponse} object.
     */
    public ErrorResponse readErrorResponse() {
        try {
            if(in().available() >= 6) {
                byte[] data = new byte[6];
                recv(data);
                return new ErrorResponse(data);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new ApnsException(e);
        } catch (ApnsException e) {
            throw e;
        }
    }

    private void _close() throws IOException {
        if (null != socket) {
            try {
                socket.close();
            } finally {
                in = null;
                out = null;
                socket = null;
            }
        }
    }

    /**
     * <p>close.</p>
     */
    @Override
    public void close() {
        try {
            _close();
        } catch (IOException e) {
            throw new ApnsException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(byte[] data) {
        try {
            out().write(data);
        } catch (IOException e) {
            try {
                _close();
            } catch (IOException e1) {
            }
            throw new ApnsException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int recv(byte[] data) {
        try {
            return in().read(data);
        } catch (IOException e) {
            try {
                _close();
            } catch (IOException e1) {
            }
            throw new ApnsException(e);
        }
    }

    /**
     * <p>socket.</p>
     *
     */
    protected void socket() throws IOException {
        if (null != socket && null != in && null != out && !socket.isClosed()) {
            return;
        } else {
            _close();
            this.socket = socketFactory.createSocket();
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
        }
    }

    /**
     * <p>out.</p>
     *
     * @return a {@link java.io.OutputStream} object.
     */
    protected OutputStream out() {
        return this.out;
    }

    /**
     * <p>in.</p>
     *
     * @return a {@link java.io.InputStream} object.
     */
    protected InputStream in() {
        return this.in;
    }

    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return a int.
     */
    public int getId() {
        return id;
    }
}
