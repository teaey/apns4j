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

    public static ApnsAsynService buildAPNSAsynService(int executorSize, KeyStoreWrapper keyStoreWrapper, AppleServer appleServer) {
        SecuritySocketFactory securitySocketFactory = buildSecuritySocketFactory(keyStoreWrapper, appleServer);
        return ApnsAsynService.newAPNSAsynService(executorSize, securitySocketFactory);
    }
}
