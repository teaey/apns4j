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

package cn.teaey.apns4j.network;

/**
 * @author teaey(xiaofei.wxf)
 * @since 1.0.3
 */
public enum ApnsGateway {
    DEVELOPMENT("gateway.sandbox.push.apple.com", 2195),
    PRODUCTION("gateway.push.apple.com", 2195),
    RESTFUL_DEVELOPMENT("api.development.push.apple.com", 443),
    RESTFUL_PRODUCTION("api.push.apple.com", 443),
    RESTFUL_DEVELOPMENT_BAK("api.development.push.apple.com", 2197),
    RESTFUL_PRODUCTION_BAK("api.push.apple.com", 2197);
    private final String host;
    private final int port;

    ApnsGateway(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String host() {
        return this.host;
    }

    public int port() {
        return this.port;
    }
}
