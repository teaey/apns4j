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
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public enum AppleGateway {
    NOTIFICATION_SERVER("gateway.push.apple.com", 2195, "gateway.sandbox.push.apple.com", 2195);
    private final String productionHost;
    private final int productionPort;
    private final String developmentHost;
    private final int developmentPort;

    AppleGateway(String productionHost, int productionPort, String developmentHost, int developmentPort) {
        this.productionHost = productionHost;
        this.productionPort = productionPort;
        this.developmentHost = developmentHost;
        this.developmentPort = developmentPort;
    }

    /**
     * <p>Getter for the field <code>productionHost</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getProductionHost() {
        return this.productionHost;
    }

    /**
     * <p>Getter for the field <code>productionPort</code>.</p>
     *
     * @return a int.
     */
    public int getProductionPort() {
        return this.productionPort;
    }

    /**
     * <p>Getter for the field <code>developmentHost</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getDevelopmentHost() {
        return this.developmentHost;
    }

    /**
     * <p>Getter for the field <code>developmentPort</code>.</p>
     *
     * @return a int.
     */
    public int getDevelopmentPort() {
        return this.developmentPort;
    }
}
