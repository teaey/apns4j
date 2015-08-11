package cn.teaey.apns4j.keystore;
import cn.teaey.apns4j.keystore.exception.InvalidKeyStoreException;
import cn.teaey.apns4j.keystore.exception.InvalidKeyStoreFormatException;
import cn.teaey.apns4j.keystore.exception.InvalidKeyStorePasswordException;
import cn.teaey.apns4j.keystore.exception.InvalidKeyStoreTypeException;

import javax.crypto.BadPaddingException;
import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class KeyStoreHelper
{
    /* PKCS12 */
    /** Constant <code>KEYSTORE_TYPE_PKCS12="PKCS12"</code> */
    public static final String KEYSTORE_TYPE_PKCS12 = "PKCS12";
    /* JKS */
    /** Constant <code>KEYSTORE_TYPE_JKS="JKS"</code> */
    public static final String KEYSTORE_TYPE_JKS    = "JKS";
    /**
     * <p>newKeyStoreWraper.</p>
     *
     * @param keyStoreMeta a {@link Object} object.
     * @param keyStorePassword a {@link String} object.
     * @return a {@link KeyStoreWrapper} object.
     * @throws cn.teaey.apns4j.keystore.exception.InvalidKeyStoreException if any.
     */
    public static KeyStoreWrapper newKeyStoreWraper(Object keyStoreMeta, String keyStorePassword) throws InvalidKeyStoreException
    {
        return newKeyStoreWraper(keyStoreMeta, KEYSTORE_TYPE_PKCS12, keyStorePassword);
    }
    /**
     * <p>newKeyStoreWraper.</p>
     *
     * @param keyStoreMeta a {@link Object} object.
     * @param keyStoreType a {@link String} object.
     * @param keyStorePassword a {@link String} object.
     * @return a {@link KeyStoreWrapper} object.
     * @throws cn.teaey.apns4j.keystore.exception.InvalidKeyStoreException if any.
     */
    public static KeyStoreWrapper newKeyStoreWraper(Object keyStoreMeta, String keyStoreType, String keyStorePassword) throws InvalidKeyStoreException
    {
        KeyStore keyStore = getKeyStore(keyStoreMeta, keyStoreType, keyStorePassword);
        return new KeyStoreWrapper(keyStore, keyStorePassword);
    }
    /**
     * <p>getKeyStore.</p>
     *
     * @param keyStoreMeta a {@link Object} object.
     * @param keyStorePassword a {@link String} object.
     * @return a {@link java.security.KeyStore} object.
     * @throws cn.teaey.apns4j.keystore.exception.InvalidKeyStoreException if any.
     */
    public static KeyStore getKeyStore(Object keyStoreMeta, String keyStorePassword) throws InvalidKeyStoreException
    {
        return getKeyStore(keyStoreMeta, KEYSTORE_TYPE_PKCS12, keyStorePassword);
    }
    /**
     * <p>getKeyStore.</p>
     *
     * @param keyStoreMeta a {@link Object} object.
     * @param keyStoreType a {@link String} object.
     * @param keyStorePassword a {@link String} object.
     * @return a {@link java.security.KeyStore} object.
     * @throws cn.teaey.apns4j.keystore.exception.InvalidKeyStoreException if any.
     */
    public static KeyStore getKeyStore(Object keyStoreMeta, String keyStoreType, String keyStorePassword) throws InvalidKeyStoreException
    {
        try
        {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            InputStream keyStoreInputStream = checkAndGetKeyStoreInputStream(keyStoreMeta);
            keyStore.load(keyStoreInputStream, keyStorePassword.toCharArray());
            return keyStore;
        } catch (KeyStoreException e)
        {
            if (e.getCause() instanceof NoSuchAlgorithmException || e.getCause() instanceof NoSuchProviderException)
            {
                throw new InvalidKeyStoreTypeException("(" + keyStoreType + ")", e);
            }
            else
            {
                throw new InvalidKeyStoreTypeException(e);
            }
        } catch (CertificateException e)
        {
            throw new InvalidKeyStoreFormatException("cant load keystore", e);
        } catch (NoSuchAlgorithmException e)
        {
            throw new InvalidKeyStoreFormatException("cant load keystore", e);
        } catch (IOException e)
        {
            if (e.getCause() instanceof BadPaddingException)
            {
                throw new InvalidKeyStorePasswordException("(" + keyStorePassword + ")", e);
            }
            else
            {
                throw new InvalidKeyStoreFormatException(e);
            }
        }
    }
    static InputStream checkAndGetKeyStoreInputStream(Object keyStore) throws InvalidKeyStoreException
    {
        if (null == keyStore)
        {
            throw new NullPointerException("keyStore cant be null");
        }
        else
            if (keyStore instanceof InputStream)
            {
                return ((InputStream) keyStore);
            }
            else
                if (keyStore instanceof File)
                {
                    try
                    {
                        return new FileInputStream((File) keyStore);
                    } catch (FileNotFoundException e)
                    {
                        throw new InvalidKeyStoreException("keyStore " + keyStore + " not exist", e);
                    }
                }
                else
                    if (keyStore instanceof byte[])
                    {
                        return new ByteArrayInputStream((byte[]) keyStore);
                    }
                    else
                        if (keyStore instanceof String)
                        {
                            try
                            {
                                //serach in classpath
                                InputStream ret = KeyStoreHelper.class.getClassLoader().getResourceAsStream((String) keyStore);
                                if (null == ret)
                                {
                                    //find in system path
                                    ret = new FileInputStream((String) keyStore);
                                }
                                return ret;
                            } catch (FileNotFoundException e)
                            {
                                throw new InvalidKeyStoreException("cant find keyStore file [" + keyStore + "]");
                            }
                        }
                        else
                        {
                            throw new InvalidKeyStoreException("invalid keystore type [" + keyStore.getClass().getName() + "] type must one of (File byte[] String InputStream KeyStore)");
                        }
    }
}
