package cn.teaey.apns4j.network;
/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class BaseAppleServer implements AppleServer
{
    public static final AppleServer PRODUCTION = new BaseAppleServer(AppleGateway.NOTIFICATION_SERVER, true);
    public static final AppleServer DEVELOPMENT = new BaseAppleServer(AppleGateway.NOTIFICATION_SERVER, false);
    private       String       proxyHost;
    private       int          proxyPort;
    private final AppleGateway appleGateway;
    private final boolean      isProduction;
    /**
     * <p>Constructor for BaseAppleServer.</p>
     *
     * @param appleGateway a {@link cn.teaey.apns4j.network.AppleGateway} object.
     * @param isProduction a boolean.
     */
    protected BaseAppleServer(AppleGateway appleGateway, boolean isProduction)
    {
        checkNull(appleGateway);
        this.appleGateway = appleGateway;
        this.isProduction = isProduction;
    }
    private void checkNull(Object object)
    {
        if (null == object)
        {
            throw new NullPointerException();
        }
    }
    /**
     * <p>getHost.</p>
     *
     * @return a {@link String} object.
     */
    public String getHost()
    {
        return isProduction ? appleGateway.getProductionHost() : appleGateway.getDevelopmentHost();
    }
    /**
     * <p>getPort.</p>
     *
     * @return a int.
     */
    public int getPort()
    {
        return isProduction ? appleGateway.getProductionPort() : appleGateway.getDevelopmentPort();
    }
    /** {@inheritDoc} */
    @Override
    public String getProxyHost()
    {
        return proxyHost;
    }
    /** {@inheritDoc} */
    @Override
    public int getProxyPort()
    {
        return proxyPort;
    }
    /** {@inheritDoc} */
    @Override
    public void setProxy(String proxyHost, int proxyPort)
    {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }
}
