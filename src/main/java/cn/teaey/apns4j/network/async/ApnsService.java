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

package cn.teaey.apns4j.network.async;

import cn.teaey.apns4j.ApnsException;
import cn.teaey.apns4j.ApnsHelper;
import cn.teaey.apns4j.network.ApnsChannelFactory;
import cn.teaey.apns4j.protocol.ApnsPayload;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author teaey
 * @since 1.0.0
 */
public class ApnsService implements PayloadSender<ApnsPayload> {
    private static final int processors = Runtime.getRuntime().availableProcessors();
    private final int size;
    private final ApnsChannelFactory apnsChannelFactory;
    private final ExecutorService executorService;
    private final ExecutorService errorExecutor;
    private final AtomicBoolean START = new AtomicBoolean(true);
    private final int tryTimes;
    private final ThreadLocal<PayloadSender<ApnsPayload>> threadSelfConnection = new ThreadLocal() {
        protected PayloadSender<ApnsPayload> initialValue() {
            return apnsChannelFactory.newChannel();
        }
    };

    public ApnsService(int executorSize, ApnsChannelFactory apnsChannelFactory, int tryTimes) {
        this.size = (executorSize > processors) ? processors : executorSize;
        this.apnsChannelFactory = apnsChannelFactory;
        this.executorService = Executors.newFixedThreadPool(this.size);
        this.errorExecutor = Executors.newFixedThreadPool(1);
        this.tryTimes = tryTimes;
    }

    public ApnsFuture send(String deviceToken, ApnsPayload payload) {
        return this.send(ApnsHelper.toByteArray(deviceToken), payload);
    }

    public ApnsFuture send(byte[] deviceToken, ApnsPayload payload) {
        if (!START.get()) {
            throw new AsyncServiceShutdownException("AsynService has shutdown");
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

    public void shutdownNow() {
        if (START.compareAndSet(true, false)) {
            executorService.shutdownNow();
        }
    }

    public void shutdown(long timeout, TimeUnit timeUnit) {
        if (START.compareAndSet(true, false)) {
            try {
                executorService.shutdown();
                executorService.awaitTermination(timeout, timeUnit);
            } catch (InterruptedException e) {
                throw new ApnsException(e);
            }
        }
    }

    //private static final Logger log = LoggerFactory.getLogger(APNSAsynService.class);
    class APNSAsynTask implements Callable<Object> {
        private final byte[] deviceToken;
        private final ApnsPayload payload;

        public APNSAsynTask(byte[] deviceToken, ApnsPayload payload) {
            this.deviceToken = deviceToken;
            this.payload = payload;
        }

        @Override
        public Object call() {
            try {
                return threadSelfConnection.get().send(deviceToken, payload);
            } catch (Exception e) {
                //log.error("Failed cause IOException deviceToken={} payload={}", deviceToken, payload.toJsonString(), e);
                return e;
            }
        }
    }
}
