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

package cn.teaey.apns4j.protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author teaey
 * @since 1.0.0
 */
public class ApnsPayload extends Payload {
    private static final String ATTR_APS = "aps";
    private static final String ATTR_ALERT = "alert";
    private static final String ATTR_BADGE = "badge";
    private static final String ATTR_SOUND = "sound";
    private static final String ATTR_ALERT_BODY = "body";
    private static final String ATTR_ALERT_TITLE = "title";
    private static final String ATTR_ALERT_ACTION_LOC_KEY = "action-loc-key";
    private static final String ATTR_ALERT_TITLE_LOC_KEY = "title-loc-key";
    private static final String ATTR_ALERT_TITLE_LOC_ARGS = "title-loc-args";
    private static final String ATTR_ALERT_LOC_KEY = "loc-key";
    private static final String ATTR_ALERT_LOC_ARGS = "loc-args";
    private static final String ATTR_ALERT_LAUNCH_IMAGE = "launch-image";
    private static final String ATTR_CONTENT_AVAILABLE = "content-available";

    private final Map<String, Object> apsDict = new HashMap<String, Object>();

    public ApnsPayload() {
        addDictionary(ATTR_APS, apsDict);
    }

    /**
     * <p>alert.</p>
     *
     * @param msg a {@link String} object.
     * @return ApnsPayload
     */
    public ApnsPayload alert(String msg) {
        apsDict.put(ATTR_ALERT, msg);
        return this;
    }

    /**
     * <p>badge.</p>
     *
     * @param num a int.
     *
     * @return ApnsPayload
     */
    public ApnsPayload badge(int num) {
        apsDict.put(ATTR_BADGE, num);
        return this;
    }

    /**
     * <p>sound.</p>
     *
     * @param sound a {@link String} object.
     * @return ApnsPayload
     */
    public ApnsPayload sound(String sound) {
        apsDict.put(ATTR_SOUND, sound);
        return this;
    }

    /**
     * <p>alertBody.</p>
     *
     * @param body a {@link String} object.
     * @return ApnsPayload
     */
    public ApnsPayload alertBody(String body) {
        ensureAlertMap().put(ATTR_ALERT_BODY, body);
        return this;
    }

    /**
     * added in iOS 8.2
     *
     * @param title
     * @return ApnsPayload
     */
    public ApnsPayload alertTitle(String title) {
        ensureAlertMap().put(ATTR_ALERT_TITLE, title);
        return this;
    }

    /**
     * added in iOS 8.2
     *
     * @param titleLocKey
     * @return ApnsPayload
     */
    public ApnsPayload alertTitleLocKey(String titleLocKey) {
        ensureAlertMap().put(ATTR_ALERT_TITLE_LOC_KEY, titleLocKey);
        return this;
    }

    /**
     * added in iOS 8.2
     *
     * @param titleLocArgs
     * @return ApnsPayload
     */
    public ApnsPayload alertTitleLocArgs(String titleLocArgs) {
        ensureAlertMap().put(ATTR_ALERT_TITLE_LOC_ARGS, titleLocArgs);
        return this;
    }

    /**
     * <p>alertActionLocKey.</p>
     *
     * @param actionLocKey a {@link String} object.
     * @return ApnsPayload
     */
    public ApnsPayload alertActionLocKey(String actionLocKey) {
        ensureAlertMap().put(ATTR_ALERT_ACTION_LOC_KEY, actionLocKey);
        return this;
    }

    /**
     * <p>alertLocKey.</p>
     *
     * @param locKey a {@link String} object.
     * @return ApnsPayload
     */
    public ApnsPayload alertLocKey(String locKey) {
        ensureAlertMap().put(ATTR_ALERT_LOC_KEY, locKey);
        return this;
    }

    /**
     * <p>alertLocArgs.</p>
     *
     * @param args a {@link java.util.List} object.
     * @return ApnsPayload
     */
    public ApnsPayload alertLocArgs(List args) {
        ensureAlertMap().put(ATTR_ALERT_LOC_ARGS, args);
        return this;
    }

    /**
     * <p>launchImage.</p>
     *
     * @param args a {@link String} object.
     * @return ApnsPayload
     */
    public ApnsPayload launchImage(String args) {
        ensureAlertMap().put(ATTR_ALERT_LAUNCH_IMAGE, args);
        return this;
    }

    private Map<String, Object> ensureAlertMap() {
        Object ret = apsDict.get(ATTR_ALERT);
        if (null == ret || ret instanceof String) {
            ret = new HashMap<String, Object>();
            apsDict.put(ATTR_ALERT, ret);
        }
        return (Map<String, Object>) ret;
    }

    public ApnsPayload silent() {
        addDictionary(ATTR_CONTENT_AVAILABLE, 1);
        return this;
    }
}
