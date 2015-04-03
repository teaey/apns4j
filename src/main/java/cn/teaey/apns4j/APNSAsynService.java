package cn.teaey.apns4j;
import cn.teaey.apns4j.network.ConnectionException;
import cn.teaey.apns4j.network.PayloadSender;
import cn.teaey.apns4j.network.SecurityConnection;
import cn.teaey.apns4j.network.SecuritySocketFactory;
import cn.teaey.apns4j.protocol.NotifyPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * User: Teaey
 * Date: 13-8-31
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class APNSAsynService implements PayloadSender<NotifyPayload>
{
    private static final Logger log = LoggerFactory.getLogger(APNSAsynService.class);
    class APNSAsynTask implements Runnable
    {
        private final byte[]        deviceToken;
        private final NotifyPayload payload;
        public APNSAsynTask(byte[] deviceToken, NotifyPayload payload)
        {
            this.deviceToken = deviceToken;
            this.payload = payload;
        }
        @Override
        public void run()
        {
            try
            {
                threadSelfConnection.get().sendAndFlush(deviceToken, payload);
            } catch (IOException e)
            {
                log.error("Failed cause IOException deviceToken={} payload={}", deviceToken, payload.toJsonString(), e);
            }
        }
    }
    /**
     * <p>sendAndFlush.</p>
     *
     * @param deviceToken a {@link String} object.
     * @param payload a {@link cn.teaey.apns4j.protocol.NotifyPayload} object.
     */
    public void sendAndFlush(String deviceToken, NotifyPayload payload)
    {
        this.sendAndFlush(APNSHelper.toByteArray(deviceToken), payload);
    }
    /**
     * <p>sendAndFlush.</p>
     *
     * @param deviceToken an array of byte.
     * @param payload a {@link cn.teaey.apns4j.protocol.NotifyPayload} object.
     */
    public void sendAndFlush(byte[] deviceToken, NotifyPayload payload)
    {
        if (!START.get())
        {
            throw new ShutdownServiceException("Asyn Service has shutdown");
        }
        executorService.submit(new APNSAsynTask(deviceToken, payload));
    }
    private static final int processors = Runtime.getRuntime().availableProcessors();
    private final int                   size;
    private final SecuritySocketFactory securityConnectionFactory;
    private final ExecutorService       executorService;
    private final AtomicBoolean                             START                = new AtomicBoolean(true);
    private final ThreadLocal<PayloadSender<NotifyPayload>> threadSelfConnection = new ThreadLocal()
    {
        protected PayloadSender<NotifyPayload> initialValue()
        {
            return SecurityConnection.newSecurityConnection(securityConnectionFactory);
        }
    };
    /**
     * <p>newAPNSAsynService.</p>
     *
     * @param executorSize a int.
     * @param securityConnectionFactory a {@link cn.teaey.apns4j.network.SecuritySocketFactory} object.
     * @return a {@link cn.teaey.apns4j.APNSAsynService} object.
     * @throws cn.teaey.apns4j.network.ConnectionException if any.
     * @throws java.io.IOException if any.
     */
    public static APNSAsynService newAPNSAsynService(int executorSize, SecuritySocketFactory securityConnectionFactory) throws ConnectionException, IOException
    {
        return new APNSAsynService(executorSize, securityConnectionFactory);
    }
    private APNSAsynService(int executorSize, SecuritySocketFactory securityConnectionFactory) throws ConnectionException, IOException
    {
        this.size = (executorSize > processors) ? processors : executorSize;
        this.securityConnectionFactory = securityConnectionFactory;
        this.executorService = Executors.newFixedThreadPool(this.size);
    }
    /**
     * <p>shutdown.</p>
     */
    public void shutdown()
    {
        if (START.compareAndSet(true, false))
        {
            executorService.shutdown();
        }
    }
}
