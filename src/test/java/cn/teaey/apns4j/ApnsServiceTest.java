package cn.teaey.apns4j;

import cn.teaey.apns4j.network.async.ApnsFuture;
import cn.teaey.apns4j.protocol.ApnsPayload;
import org.junit.After;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author teaey(xiaofei.wxf)
 * @since 1.0.3
 */
public class ApnsServiceTest extends ApnsBaseTestCase {

    @Test
    public void batch() throws ExecutionException, InterruptedException {
        ApnsPayload apnsPayload = Apns4j.newPayload();
        apnsPayload.alert("ABC");
        ApnsFuture apnsFuture = apnsService.send(TestConts.deviceToken, apnsPayload);
        ApnsFuture apnsFuture1 = apnsService.send(TestConts.deviceToken, apnsPayload);

        apnsService.shutdown(100, TimeUnit.SECONDS);
    }

    @After
    public void destroy() {
        apnsChannel.close();
        apnsService.shutdown();
    }

}
