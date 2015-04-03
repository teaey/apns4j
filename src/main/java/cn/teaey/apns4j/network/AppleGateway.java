package cn.teaey.apns4j.network;
/**
 * User: Teaey
 * Date: 13-8-29
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public enum AppleGateway
{
    NOTIFICATION_SERVER("gateway.push.apple.com", 2195, "gateway.sandbox.push.apple.com", 2195);
    /** Constant <code>ENV_PRODUCTION=true</code> */
    public static final boolean ENV_PRODUCTION  = true;
    /** Constant <code>ENV_DEVELOPMENT=false</code> */
    public static final boolean ENV_DEVELOPMENT = false;
    /**
     *
     */
    private final String productionHost;
    private final int    productionPort;
    private final String developmentHost;
    private final int    developmentPort;
    private AppleGateway(String productionHost, int productionPort, String developmentHost, int developmentPort)
    {
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
    public String getProductionHost()
    {
        return this.productionHost;
    }
    /**
     * <p>Getter for the field <code>productionPort</code>.</p>
     *
     * @return a int.
     */
    public int getProductionPort()
    {
        return this.productionPort;
    }
    /**
     * <p>Getter for the field <code>developmentHost</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getDevelopmentHost()
    {
        return this.developmentHost;
    }
    /**
     * <p>Getter for the field <code>developmentPort</code>.</p>
     *
     * @return a int.
     */
    public int getDevelopmentPort()
    {
        return this.developmentPort;
    }
}
