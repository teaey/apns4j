package cn.teaey.apns4j;

import cn.teaey.apns4j.network.ApnsChannel;
import cn.teaey.apns4j.network.ApnsChannelFactory;
import cn.teaey.apns4j.network.ApnsGateway;
import cn.teaey.apns4j.network.ApnsNettyChannel;
import cn.teaey.apns4j.network.async.ApnsFuture;
import cn.teaey.apns4j.network.async.ApnsService;
import cn.teaey.apns4j.protocol.ApnsPayload;
import org.junit.After;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author teaey(xiaofei.wxf)
 * @since 1.0.3
 */
public class MainTest {
    static final ApnsChannelFactory apnsChannelFactory = Apns4j.newChannelFactoryBuilder().keyStoreMeta(TestConts.keyStorePath).keyStorePwd(TestConts.keyStorePwd).apnsGateway(ApnsGateway.DEVELOPMENT).build();
    static final ApnsNettyChannel apnsChannel = apnsChannelFactory.newChannel();
    static final ApnsService apnsService = new ApnsService(3, apnsChannelFactory, 3);
    public static void main(String[] args) {
        ApnsPayload apnsPayload = Apns4j.newPayload()
                .alertTitle("Title")
                .alertBody("Pushed by apns4j")
                .extend("k", "v")
                .sound("default");
        //send via channel
        apnsChannel.send(TestConts.deviceToken, apnsPayload);
        //send async via service
        ApnsFuture apnsFuture = apnsService.send(TestConts.deviceToken, apnsPayload);
        apnsService.shutdown(3, TimeUnit.SECONDS);
    }
}
