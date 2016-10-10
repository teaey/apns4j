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

import cn.teaey.apns4j.keystore.KeyStoreGetter;
import cn.teaey.apns4j.keystore.KeyStoreType;
import cn.teaey.apns4j.network.ApnsChannelFactory;
import cn.teaey.apns4j.network.ApnsGateway;
import cn.teaey.apns4j.network.SecuritySocketFactory;
import cn.teaey.apns4j.protocol.ApnsPayload;

import java.io.File;
import java.io.InputStream;

/**
 * @author xiaofei.wxf
 * @since 1.0.2
 */
public class Apns4j {
    /**
     * <p>newNotifyPayload.</p>
     *
     * @return a {@link ApnsPayload} object.
     */
    public static final ApnsPayload newPayload() {
        return new ApnsPayload();
    }

    public static final ApnsChannelFactoryBuilder newChannelFactoryBuilder() {
        return new ApnsChannelFactoryBuilder();
    }

    public static final class ApnsChannelFactoryBuilder {
        private Object keyStoreMeta;
        private String keyStorePwd;
        private ApnsGateway apnsGateway = ApnsGateway.DEVELOPMENT;
        private KeyStoreType keyStoreType = KeyStoreType.PKCS12;

        public ApnsChannelFactoryBuilder keyStoreMeta(Object keyStoreMeta) {
            ApnsHelper.checkNullThrowException(keyStoreMeta, "keyStoreMeta");
            this.keyStoreMeta = keyStoreMeta;
            return this;
        }

        public ApnsChannelFactoryBuilder keyStorePwd(String keyStorePwd) {
            ApnsHelper.checkNullThrowException(keyStorePwd, "keyStorePwd");
            this.keyStorePwd = keyStorePwd;
            return this;
        }

        public ApnsChannelFactoryBuilder apnsGateway(ApnsGateway apnsGateway) {
            ApnsHelper.checkNullThrowException(apnsGateway, "apnsGateway");
            this.apnsGateway = apnsGateway;
            return this;
        }

        public ApnsChannelFactoryBuilder keyStoreType(KeyStoreType keyStoreType) {
            ApnsHelper.checkNullThrowException(keyStoreType, "keyStoreType");
            this.keyStoreType = keyStoreType;
            return this;
        }

        public ApnsChannelFactory build() {
            KeyStoreGetter keyStoreGetter = null;
            if (keyStoreMeta instanceof String) {
                keyStoreGetter = new KeyStoreGetter((String) this.keyStoreMeta, this.keyStorePwd, this.keyStoreType);
            } else if (keyStoreMeta instanceof File) {
                keyStoreGetter = new KeyStoreGetter((File) this.keyStoreMeta, this.keyStorePwd, this.keyStoreType);
            } else if (keyStoreMeta instanceof InputStream) {
                keyStoreGetter = new KeyStoreGetter((InputStream) this.keyStoreMeta, this.keyStorePwd, this.keyStoreType);
            }
            if (null == keyStoreGetter) {
                throw new IllegalArgumentException(keyStoreMeta.getClass().getName());
            }
            SecuritySocketFactory securitySocketFactory = new SecuritySocketFactory(this.apnsGateway.host(), this.apnsGateway.port(), keyStoreGetter.keyStore(), keyStoreGetter.keyStorePwd());
            return new ApnsChannelFactory(securitySocketFactory);
        }
    }

}
