package cn.teaey.apns4j;

import cn.teaey.apns4j.network.ApnsFuture;
import cn.teaey.apns4j.network.PayloadSender;
import cn.teaey.apns4j.network.SecurityConnection;
import cn.teaey.apns4j.network.SecuritySocketFactory;
import cn.teaey.apns4j.protocol.NotifyPayload;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class APNSAsynService implements PayloadSender<NotifyPayload> {
    public static final Object SEND_SUCCESS = new Object();
    private static final int processors = Runtime.getRuntime().availableProcessors();
    private final int size;
    private final SecuritySocketFactory securitySocketFactory;
    private final ExecutorService executorService;
    private final ExecutorService errorExecutor;
    private final AtomicBoolean START = new AtomicBoolean(true);
    private final int tryTimes;
    private final ThreadLocal<PayloadSender<NotifyPayload>> threadSelfConnection = new ThreadLocal() {
        protected PayloadSender<NotifyPayload> initialValue() {
            return SecurityConnection.newSecurityConnection(securitySocketFactory, tryTimes);
        }
    };
    private APNSAsynService(int executorSize, SecuritySocketFactory securityConnectionFactory, int tryTimes) {
        this.size = (executorSize > processors) ? processors : executorSize;
        this.securitySocketFactory = securityConnectionFactory;
        this.executorService = Executors.newFixedThreadPool(this.size);
        this.errorExecutor = Executors.newFixedThreadPool(1);
        this.tryTimes = tryTimes;
    }

    /**
     * <p>newAPNSAsynService.</p>
     *
     * @param executorSize          a int.
     * @param securitySocketFactory a {@link cn.teaey.apns4j.network.SecuritySocketFactory} object.
     * @return a {@link cn.teaey.apns4j.APNSAsynService} object.
     */
    public static APNSAsynService newAPNSAsynService(int executorSize, SecuritySocketFactory securitySocketFactory) {
        return newAPNSAsynService(executorSize, securitySocketFactory, SecurityConnection.DEFAULT_TRY_TIMES);
    }

    /**
     * <p>newAPNSAsynService.</p>
     *
     * @param executorSize          a int.
     * @param securitySocketFactory a {@link cn.teaey.apns4j.network.SecuritySocketFactory} object.
     * @param tryTimes
     * @return a {@link cn.teaey.apns4j.APNSAsynService} object.
     */
    public static APNSAsynService newAPNSAsynService(int executorSize, SecuritySocketFactory securitySocketFactory, int tryTimes) {
        return new APNSAsynService(executorSize, securitySocketFactory, tryTimes);
    }

    /**
     * <p>sendAndFlush.</p>
     *
     * @param deviceToken a {@link String} object.
     * @param payload     a {@link cn.teaey.apns4j.protocol.NotifyPayload} object.
     */
    public ApnsFuture sendAndFlush(String deviceToken, NotifyPayload payload) {
        return this.sendAndFlush(APNSHelper.toByteArray(deviceToken), payload);
    }

    /**
     * <p>sendAndFlush.</p>
     *
     * @param deviceToken an array of byte.
     * @param payload     a {@link cn.teaey.apns4j.protocol.NotifyPayload} object.
     */
    public ApnsFuture sendAndFlush(byte[] deviceToken, NotifyPayload payload) {
        if (!START.get()) {
            throw new ShutdownServiceException("AsynService has shutdown");
        }
        Future f = executorService.submit(new APNSAsynTask(deviceToken, payload));
        return new ApnsFuture(f, deviceToken, payload);
    }

    /**
     * <p>shutdown.</p>
     */
    public void shutdown() {
        if (START.compareAndSet(true, false)) {
            executorService.shutdown();
        }
    }

    //private static final Logger log = LoggerFactory.getLogger(APNSAsynService.class);
    class APNSAsynTask implements Callable<Object> {
        private final byte[] deviceToken;
        private final NotifyPayload payload;

        public APNSAsynTask(byte[] deviceToken, NotifyPayload payload) {
            this.deviceToken = deviceToken;
            this.payload = payload;
        }

        @Override
        public Object call() {
            try {
                threadSelfConnection.get().sendAndFlush(deviceToken, payload);
                return SEND_SUCCESS;
            } catch (Exception e) {
                //log.error("Failed cause IOException deviceToken={} payload={}", deviceToken, payload.toJsonString(), e);
                return e;
            }
        }
    }
}
