package cn.teaey.apns4j.network;

import cn.teaey.apns4j.protocol.NotifyPayload;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaofei.wxf
 * @date 2015/8/11
 */
public class ApnsFuture implements Future {
    private final Future future;
    private final byte[] deviceToken;
    private final NotifyPayload payload;
    public ApnsFuture(Future future, byte[] deviceToken, NotifyPayload payload) {
        this.future = future;
        this.deviceToken = deviceToken;
        this.payload = payload;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return future.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return future.isDone();
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return future.get();
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return future.get(timeout, unit);
    }

    public byte[] getDeviceToken() {
        return deviceToken;
    }

    public NotifyPayload getPayload() {
        return payload;
    }
}
