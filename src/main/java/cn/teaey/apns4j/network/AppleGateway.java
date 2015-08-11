package cn.teaey.apns4j.network;
/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public enum AppleGateway
{
    NOTIFICATION_SERVER("gateway.push.apple.com", 2195, "gateway.sandbox.push.apple.com", 2195);
    private final String productionHost;
    private final int    productionPort;
    private final String developmentHost;
    private final int    developmentPort;
    AppleGateway(String productionHost, int productionPort, String developmentHost, int developmentPort)
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
