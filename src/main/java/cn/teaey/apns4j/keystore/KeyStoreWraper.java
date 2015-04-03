package cn.teaey.apns4j.keystore;
import java.security.KeyStore;

/**
 * User: Teaey
 * Date: 13-8-30
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class KeyStoreWraper
{
    private final KeyStore keyStore;
    private final String   keyStorePassword;
    /**
     * <p>Constructor for KeyStoreWraper.</p>
     *
     * @param keyStore a {@link java.security.KeyStore} object.
     * @param keyStorePassword a {@link String} object.
     */
    public KeyStoreWraper(KeyStore keyStore, String keyStorePassword)
    {
        this.keyStore = keyStore;
        this.keyStorePassword = keyStorePassword;
    }
    /**
     * <p>Getter for the field <code>keyStore</code>.</p>
     *
     * @return a {@link java.security.KeyStore} object.
     */
    public KeyStore getKeyStore()
    {
        return keyStore;
    }
    /**
     * <p>Getter for the field <code>keyStorePassword</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getKeyStorePassword()
    {
        return keyStorePassword;
    }
}
