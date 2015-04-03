package cn.teaey.apns4j.network;
/**
 * User: Teaey
 * Date: 13-8-29
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public interface AppleServer
{
    /**
     * <p>getHost.</p>
     *
     * @return a {@link String} object.
     */
    public String getHost();
    /**
     * <p>getPort.</p>
     *
     * @return a int.
     */
    public int getPort();
    /**
     * <p>getProxyHost.</p>
     *
     * @return a {@link String} object.
     */
    public String getProxyHost();
    /**
     * <p>getProxyPort.</p>
     *
     * @return a int.
     */
    public int getProxyPort();
    /**
     * <p>setProxy.</p>
     *
     * @param proxyHost a {@link String} object.
     * @param proxyPort a int.
     */
    public void setProxy(String proxyHost, int proxyPort);
}
