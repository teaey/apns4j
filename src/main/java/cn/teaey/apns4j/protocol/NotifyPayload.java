package cn.teaey.apns4j.protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class NotifyPayload extends Payload {
    private static final String ATTR_APS = "aps";
    private static final String ATTR_ALERT = "alert";
    private static final String ATTR_BADGE = "badge";
    private static final String ATTR_SOUND = "sound";
    private static final String ATTR_ALERT_BODY = "body";
    private static final String ATTR_ALERT_ACTION_LOC_KEY = "action-loc-key";
    private static final String ATTR_ALERT_LOC_KEY = "loc-key";
    private static final String ATTR_ALERT_LOC_ARGS = "loc-args";
    private static final String ATTR_ALERT_LAUNCH_IMAGE = "launch-image";

    private final Map<String, Object> apsDict = new HashMap<String, Object>();

    /**
     * <p>newNotifyPayload.</p>
     *
     * @return a {@link cn.teaey.apns4j.protocol.NotifyPayload} object.
     */
    public static final NotifyPayload newNotifyPayload() {
        return new NotifyPayload();
    }

    private NotifyPayload() {
        addDictionary(ATTR_APS, apsDict);
    }

    /**
     * <p>alert.</p>
     *
     * @param msg a {@link String} object.
     */
    public NotifyPayload alert(String msg) {
        apsDict.put(ATTR_ALERT, msg);
        return this;
    }

    /**
     * <p>badge.</p>
     *
     * @param num a int.
     */
    public NotifyPayload badge(int num) {
        apsDict.put(ATTR_BADGE, num);
        return this;
    }

    /**
     * <p>sound.</p>
     *
     * @param sound a {@link String} object.
     */
    public NotifyPayload sound(String sound) {
        apsDict.put(ATTR_SOUND, sound);
        return this;
    }

    /**
     * <p>alertBody.</p>
     *
     * @param body a {@link String} object.
     */
    public NotifyPayload alertBody(String body) {
        ensureAlertMap().put(ATTR_ALERT_BODY, body);
        return this;
    }

    /**
     * <p>alertActionLocKey.</p>
     *
     * @param actionLocKey a {@link String} object.
     */
    public NotifyPayload alertActionLocKey(String actionLocKey) {
        ensureAlertMap().put(ATTR_ALERT_ACTION_LOC_KEY, actionLocKey);
        return this;
    }

    /**
     * <p>alertLocKey.</p>
     *
     * @param locKey a {@link String} object.
     */
    public NotifyPayload alertLocKey(String locKey) {
        ensureAlertMap().put(ATTR_ALERT_LOC_KEY, locKey);
        return this;
    }

    /**
     * <p>alertLocArgs.</p>
     *
     * @param args a {@link java.util.List} object.
     */
    public NotifyPayload alertLocArgs(List args) {
        ensureAlertMap().put(ATTR_ALERT_LOC_ARGS, args);
        return this;
    }

    /**
     * <p>launchImage.</p>
     *
     * @param args a {@link String} object.
     */
    public NotifyPayload launchImage(String args) {
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
}
