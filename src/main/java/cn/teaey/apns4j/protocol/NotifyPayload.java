package cn.teaey.apns4j.protocol;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Teaey
 * Date: 13-8-31
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class NotifyPayload extends Payload
{
    private final        Map<String, Object> apsDict                   = new HashMap<String, Object>();
    private static final String              ATTR_APS                  = "aps";
    private static final String              ATTR_ALERT                = "alert";
    private static final String              ATTR_BADGE                = "badge";
    private static final String              ATTR_SOUND                = "sound";
    private static final String              ATTR_ALERT_BODY           = "body";
    private static final String              ATTR_ALERT_ACTION_LOC_KEY = "action-loc-key";
    private static final String              ATTR_ALERT_LOC_KEY        = "loc-key";
    private static final String              ATTR_ALERT_LOC_ARGS       = "loc-args";
    private static final String              ATTR_ALERT_LAUNCH_IMAGE   = "launch-image";
    /**
     * <p>newNotifyPayload.</p>
     *
     * @return a {@link cn.teaey.apns4j.protocol.NotifyPayload} object.
     */
    public static NotifyPayload newNotifyPayload()
    {
        return new NotifyPayload();
    }
    private NotifyPayload()
    {
        addDictionary(ATTR_APS, apsDict);
    }
    /**
     * <p>alert.</p>
     *
     * @param msg a {@link String} object.
     */
    public void alert(String msg)
    {
        apsDict.put(ATTR_ALERT, msg);
    }
    /**
     * <p>badge.</p>
     *
     * @param num a int.
     */
    public void badge(int num)
    {
        apsDict.put(ATTR_BADGE, num);
    }
    /**
     * <p>sound.</p>
     *
     * @param sound a {@link String} object.
     */
    public void sound(String sound)
    {
        apsDict.put(ATTR_SOUND, sound);
    }
    private Map<String, Object> ensureAlertMap()
    {
        Object ret = apsDict.get(ATTR_ALERT);
        if (null == ret || ret instanceof String)
        {
            ret = new HashMap<String, Object>();
            apsDict.put(ATTR_ALERT, ret);
        }
        return (Map<String, Object>) ret;
    }
    /**
     * <p>alertBody.</p>
     *
     * @param body a {@link String} object.
     */
    public void alertBody(String body)
    {
        ensureAlertMap().put(ATTR_ALERT_BODY, body);
    }
    /**
     * <p>alertActionLocKey.</p>
     *
     * @param actionLocKey a {@link String} object.
     */
    public void alertActionLocKey(String actionLocKey)
    {
        ensureAlertMap().put(ATTR_ALERT_ACTION_LOC_KEY, actionLocKey);
    }
    /**
     * <p>alertLocKey.</p>
     *
     * @param locKey a {@link String} object.
     */
    public void alertLocKey(String locKey)
    {
        ensureAlertMap().put(ATTR_ALERT_LOC_KEY, locKey);
    }
    /**
     * <p>alertLocArgs.</p>
     *
     * @param args a {@link java.util.List} object.
     */
    public void alertLocArgs(List args)
    {
        ensureAlertMap().put(ATTR_ALERT_LOC_ARGS, args);
    }
    /**
     * <p>launchImage.</p>
     *
     * @param args a {@link String} object.
     */
    public void launchImage(String args)
    {
        ensureAlertMap().put(ATTR_ALERT_LAUNCH_IMAGE, args);
    }
}
