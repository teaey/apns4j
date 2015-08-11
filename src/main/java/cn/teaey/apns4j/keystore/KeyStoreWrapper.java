package cn.teaey.apns4j.keystore;

import java.security.KeyStore;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class KeyStoreWrapper {
    private final KeyStore keyStore;
    private final String keyStorePassword;

    /**
     * <p>Constructor for KeyStoreWraper.</p>
     *
     * @param keyStore         a {@link java.security.KeyStore} object.
     * @param keyStorePassword a {@link String} object.
     */
    public KeyStoreWrapper(KeyStore keyStore, String keyStorePassword) {
        this.keyStore = keyStore;
        this.keyStorePassword = keyStorePassword;
    }

    /**
     * <p>Getter for the field <code>keyStore</code>.</p>
     *
     * @return a {@link java.security.KeyStore} object.
     */
    public KeyStore getKeyStore() {
        return keyStore;
    }

    /**
     * <p>Getter for the field <code>keyStorePassword</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getKeyStorePassword() {
        return keyStorePassword;
    }
}
