package cn.teaey.apns4j;

import cn.teaey.apns4j.protocol.ApnsPayload;
import org.junit.After;
import org.junit.Test;

/**
 * @author teaey(xiaofei.wxf)
 * @since 1.0.3
 */
public class AlertTest extends ApnsBaseTestCase {

    @Test
    public void alert() throws InterruptedException {

        //create & init notify payload
        ApnsPayload apnsPayload = Apns4j.newPayload()
                .alertTitle("Title")
                .alertBody("Pushed by apns4j")
                .sound("default");

        //send via channel
        apnsChannel.send(TestConts.deviceToken, apnsPayload);

        //in the end
        apnsChannel.close();

    }


    @After
    public void destory() {
        apnsChannel.close();
    }
}
