/*
 *
 *  * Copyright 2015 The Apns4j Project
 *  *
 *  * The Netty Project licenses this file to you under the Apache License,
 *  * version 2.0 (the "License"); you may not use this file except in compliance
 *  * with the License. You may obtain a copy of the License at:
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  * License for the specific language governing permissions and limitations
 *  * under the License.
 *
 */

package cn.teaey.apns4j.network;

import cn.teaey.apns4j.ApnsException;
import cn.teaey.apns4j.ApnsHelper;
import cn.teaey.apns4j.network.async.ApnsFuture;
import cn.teaey.apns4j.network.async.PayloadSender;
import cn.teaey.apns4j.protocol.ApnsPayload;
import cn.teaey.apns4j.protocol.ErrorResp;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author teaey(xiaofei.wxf)
 * @since 1.0.3
 */
public class ApnsChannel implements Channel, PayloadSender<ApnsPayload> {
    //private static final Logger log               = LoggerFactory.getLogger(ApnsChannel.class);
    public static final int DEFAULT_TRY_TIMES = 3;
    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    private final int id;
    private final SecuritySocketFactory socketFactory;
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private SSLSocket socket;
    private InputStream in;
    private OutputStream out;
    private int tryTimes;

    public ApnsChannel(SecuritySocketFactory socketFactory) {
        this(socketFactory, DEFAULT_TRY_TIMES);
    }

    public ApnsChannel(SecuritySocketFactory socketFactory, int tryTimes) {
        this.socketFactory = socketFactory;
        this.tryTimes = tryTimes;
        this.id = COUNTER.incrementAndGet();
    }


    /**
     * <p>flush.</p>
     *
     * @throws java.io.IOException if any.
     */
    private void flush() throws IOException {
        checkClosed();
        out().flush();
    }

    public ApnsFuture send(byte[] deviceTokenBytes, ApnsPayload apnsPayload, int tryTimes) {
        checkClosed();
        if (tryTimes < 1) {
            tryTimes = 1;
        }
        ApnsHelper.checkDeviceToken(deviceTokenBytes);
        String jsonString = apnsPayload.toJsonString();
        byte[] binaryData = ApnsHelper.toRequestBytes(deviceTokenBytes, jsonString, apnsPayload.getIdentifier(), apnsPayload.getExpiry());
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
     * Send a payload with a devicetoken bytes(32 bytes)
     *
     * @param deviceTokenBytes an array of byte.
     * @param apnsPayload      a {@link ApnsPayload} object.
     */
    public ApnsFuture send(byte[] deviceTokenBytes, ApnsPayload apnsPayload) {
        checkClosed();
        return this.send(deviceTokenBytes, apnsPayload, tryTimes);
    }

    /**
     * Send a payload with a devicetoken string(length 64)
     *
     * @param deviceTokenString a {@link String} object.
     * @param apnsPayload       a {@link ApnsPayload} object.
     * @param tryTimes retry times
     * @return feture
     */
    public ApnsFuture send(String deviceTokenString, ApnsPayload apnsPayload, int tryTimes) {
        checkClosed();
        return send(ApnsHelper.toByteArray(deviceTokenString), apnsPayload, tryTimes);
    }

    /**
     * Send a payload with a devicetoken string(length 64)
     *
     * @param deviceTokenString a {@link String} object.
     * @param apnsPayload       a {@link ApnsPayload} object.
     */
    public ApnsFuture send(String deviceTokenString, ApnsPayload apnsPayload) {
        checkClosed();
        return this.send(ApnsHelper.toByteArray(deviceTokenString), apnsPayload);
    }

    /**
     * <p>recvErrorResp.</p>
     *
     * @return a {@link ErrorResp} object.
     */
    public ErrorResp recvErrorResp() {
        checkClosed();
        try {
            if (null == in()) {
                return null;
            }
            if (in().available() > 0) {
                byte[] data = new byte[6];
                recv(data);
                return new ErrorResp(data);
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
            if (this.closed.compareAndSet(false, true)) {
                _close();
            }
        } catch (IOException e) {
            throw new ApnsException(e);
        }
    }

    private void checkClosed() {
        if (closed.get()) {
            throw new IllegalStateException("Channel closed, get a new channel from channel factory");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(byte[] data) {
        checkClosed();
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
        checkClosed();
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
     * @throws IOException
     */
    protected void socket() throws IOException {
        checkClosed();
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
