package cn.teaey.apns4j;

import cn.teaey.apns4j.keystore.InvalidKeyStoreException;
import cn.teaey.apns4j.keystore.KeyStoreHelper;
import cn.teaey.apns4j.keystore.KeyStoreWrapper;
import cn.teaey.apns4j.network.*;
import cn.teaey.apns4j.protocol.NotifyPayload;

/**
 * @author xiaofei.wxf
 * @date 2015/8/11
 * @since 1.0.2
 */
public class Apns4j {
    public static final AppleServer SERVER_PRODUCTION = BaseAppleServer.PRODUCTION;
    public static final AppleServer SERVER_DEVELOPMENT = BaseAppleServer.DEVELOPMENT;

    public static final NotifyPayload buildNotifyPayload() {
        return NotifyPayload.newNotifyPayload();
    }

    public static final KeyStoreWrapper buildKeyStoreWraper(Object keyStoreMeta, String keyStorePassword) {
        return KeyStoreHelper.newKeyStoreWraper(keyStoreMeta, keyStorePassword);
    }

    private static final SecuritySocketFactory buildSecuritySocketFactory(KeyStoreWrapper keyStoreWrapper, AppleServer appleServer) {
        try {
            return SecuritySocketFactory.newBuilder().keyStoreWrapper(keyStoreWrapper).appleServer(appleServer).build();
        } catch (InvalidKeyStoreException e) {
            throw new ApnsException(e);
        }
    }

    public static final SecurityConnection buildSecurityConnection(KeyStoreWrapper keyStoreWrapper, AppleServer appleServer) {
        SecuritySocketFactory factory = buildSecuritySocketFactory(keyStoreWrapper, appleServer);
        return SecurityConnection.newSecurityConnection(factory);
    }

    public static APNSAsynService buildAPNSAsynService(int executorSize, KeyStoreWrapper keyStoreWrapper, AppleServer appleServer) {
        SecuritySocketFactory securitySocketFactory = buildSecuritySocketFactory(keyStoreWrapper, appleServer);
        return APNSAsynService.newAPNSAsynService(executorSize, securitySocketFactory);
    }
}
