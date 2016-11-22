package cn.teaey.apns4j;

import cn.teaey.apns4j.protocol.ApnsPayload;
import org.junit.After;
import org.junit.Test;

/**
 * @author teaey(xiaofei.wxf)
 * @since 1.0.3
 */
public class AlertTest {

    @Test
    public void alert() throws InterruptedException {
        //create & init notify payload
        ApnsPayload apnsPayload = Apns4j.newPayload()
                .alertTitle("Title")
                .alertBody("Pushed by apns4j")
                .extend("k", "v")
                .sound("default");
        System.out.println(apnsPayload.toJsonString());
    }
}
