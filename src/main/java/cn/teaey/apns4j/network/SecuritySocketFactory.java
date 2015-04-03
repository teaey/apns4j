package cn.teaey.apns4j.network;

import cn.teaey.apns4j.keystore.KeyStoreWraper;
import cn.teaey.apns4j.keystore.exception.InvalidKeyStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * User: Teaey
 * Date: 13-8-30
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public final class SecuritySocketFactory
{
    private static final Logger log       = LoggerFactory.getLogger(SecuritySocketFactory.class);
    /* The algorithm used by KeyManagerFactory */
    private static final String ALGORITHM = ((Security.getProperty("ssl.KeyManagerFactory.algorithm") == null) ? "sunx509" : Security.getProperty("ssl.KeyManagerFactory.algorithm"));
    /* The protocol used to create the SSLSocket */
    private static final String PROTOCOL  = "TLS";
    //    static
    //    {
    //        Security.addProvider(new BouncyCastleProvider());
    //    }
    private final String           host;
    private final int              port;
    private final KeyStore         keyStore;
    private final String           keyStorePassword;
    private final SSLSocketFactory sslSocketFactory;
    private TrustManager[] trustManagers = new TrustManager[]{new ServerTrustingTrustManager()};
    private class ServerTrustingTrustManager implements X509TrustManager
    {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {
            throw new CertificateException("not trusted");
        }
        public void checkServerTrusted(X509Certificate[] chain, String authType)
        {
            // trust all servers
        }
        public X509Certificate[] getAcceptedIssuers()
        {
            return null;
        }
    }
    public static final class Builder
    {
        private Builder()
        {
        }
        public static Builder newBuilder()
        {
            return new Builder();
        }
        private String   host;
        private int      port;
        private KeyStore keyStore;
        private String   keyStorePassword;
        public SecuritySocketFactory.Builder keyStoreWrapper(KeyStoreWraper keyStoreWraper)
        {
            this.keyStore = keyStoreWraper.getKeyStore();
            this.keyStorePassword = keyStoreWraper.getKeyStorePassword();
            return this;
        }
        public SecuritySocketFactory.Builder appleServer(AppleServer appleServer)
        {
            this.host = appleServer.getHost();
            this.port = appleServer.getPort();
            return this;
        }
        public SecuritySocketFactory.Builder host(String host)
        {
            this.host = host;
            return this;
        }
        public SecuritySocketFactory.Builder port(int port)
        {
            this.port = port;
            return this;
        }
        public SecuritySocketFactory.Builder keyStore(KeyStore keyStore)
        {
            this.keyStore = keyStore;
            return this;
        }
        public SecuritySocketFactory.Builder keyStorePassword(String keyStorePassword)
        {
            if (null == keyStorePassword)
            {
                keyStorePassword = "";
            }
            this.keyStorePassword = keyStorePassword;
            return this;
        }
        public SecuritySocketFactory build() throws InvalidKeyStoreException
        {
            if (host == null)
            {
                throw new NullPointerException("host");
            }
            if (keyStore == null)
            {
                throw new NullPointerException("keystore");
            }
            if (keyStorePassword == null)
            {
                throw new NullPointerException("keyStorePassword");
            }
            return new SecuritySocketFactory(host, port, keyStore, keyStorePassword);
        }
    }
    private SecuritySocketFactory(String host, int port, KeyStore keyStore, String keyStorePassword) throws InvalidKeyStoreException
    {
        this.host = host;
        this.port = port;
        this.keyStore = keyStore;
        this.keyStorePassword = keyStorePassword;
        this.sslSocketFactory = createSSLSocketFactoryWithTrustManagers(trustManagers);
    }
    /**
     * <p>createSSLSocketFactoryWithTrustManagers.</p>
     *
     * @param trustManagers an array of {@link javax.net.ssl.TrustManager} objects.
     * @return a {@link javax.net.ssl.SSLSocketFactory} object.
     * @throws cn.teaey.apns4j.keystore.exception.InvalidKeyStoreException if any.
     */
    protected SSLSocketFactory createSSLSocketFactoryWithTrustManagers(TrustManager[] trustManagers) throws InvalidKeyStoreException
    {
        log.info("Creating SSLSocketFactory WithTrustManagers :" + Arrays.toString(trustManagers));
        // Get a KeyManager and initialize it
        try
        {
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(ALGORITHM);
            kmf.init(keyStore, keyStorePassword.toCharArray());
            // Get the SSLContext to help create SSLSocketFactory
            SSLContext sslc = SSLContext.getInstance(PROTOCOL);
            sslc.init(kmf.getKeyManagers(), trustManagers, null);
            return sslc.getSocketFactory();
        } catch (Exception e)
        {
            throw new InvalidKeyStoreException(e);
        }
    }
    /**
     * Create a SSLSocket which will be used to send data to Apple
     *
     * @return the SSLSocket
     * @throws cn.teaey.apns4j.network.ConnectionException if any.
     */
    public SSLSocket createSocket() throws ConnectionException
    {
        log.info("Creating SSLSocket to " + host + ":" + port);
        try
        {
            return (SSLSocket) sslSocketFactory.createSocket(host, port);
        } catch (Exception e)
        {
            throw new ConnectionException(e);
        }
    }
}
