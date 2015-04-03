package cn.teaey.apns4j.network;
/**
 * User: Teaey
 * Date: 13-8-29
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class AppleNotificationServer extends BaseAppleServer
{
    private AppleNotificationServer(boolean isProduction)
    {
        super(AppleGateway.NOTIFICATION_SERVER, isProduction);
    }
    private static final AppleNotificationServer INS_PRODUCTION  = new AppleNotificationServer(true);
    private static final AppleNotificationServer INS_DEVELOPMENT = new AppleNotificationServer(false);
    /**
     * <p>get.</p>
     *
     * @param isProduction a boolean.
     * @return a {@link cn.teaey.apns4j.network.AppleNotificationServer} object.
     */
    public static AppleNotificationServer get(boolean isProduction)
    {
        return isProduction ? INS_PRODUCTION : INS_DEVELOPMENT;
    }
}

