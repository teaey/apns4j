package cn.teaey.apns4j;

import cn.teaey.apns4j.protocol.ApnsPayload;
import org.junit.After;
import org.junit.Test;

/**
 * @author teaey(xiaofei.wxf)
 * @since 1.0.3
 */
public class BadgeTest extends ApnsBaseTestCase {

//    @Test
    public void badge() {
        ApnsPayload payload = Apns4j.newPayload()
                .badge(4);

        apnsChannel.send(TestConts.deviceToken, payload);
    }

    @After
    public void destory() {
        apnsChannel.close();
    }

}
