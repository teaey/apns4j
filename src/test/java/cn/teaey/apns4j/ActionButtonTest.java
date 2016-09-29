package cn.teaey.apns4j;

import cn.teaey.apns4j.protocol.ApnsPayload;
import org.junit.After;
import org.junit.Test;

/**
 * @author teaey(xiaofei.wxf)
 * @since 1.0.3
 */
public class ActionButtonTest extends ApnsBaseTestCase {

    @Test
    public void actionLocKey() {
        ApnsPayload apnsPayload = Apns4j.newPayload()
                .alertBody("Push by apns4j")
                .alertActionLocKey("FixMe");

        apnsChannel.send(TestConts.deviceToken, apnsPayload);


    }

    @After
    public void destory() {
        apnsChannel.close();
    }
}
