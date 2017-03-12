package cn.teaey.apns4j.network;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;

public class SSLUtil {
	public static final String DEFAULT_STRINGPROTOCOL = "TLS";
	public static final String DEFAULT_ALGORITHM = "SunX509";
	
	public static SSLContext initSSLContext(String keystore, String password) {
		return initSSLContext(keystore, password, DEFAULT_STRINGPROTOCOL, DEFAULT_ALGORITHM);
	}
	
	public static SSLContext initSSLContext(String keystore, String password, String protocol, String algorithm) {
		FileInputStream keystoreInputStream = null;
		SSLContext sslContext = null;
		try {
			keystoreInputStream = new FileInputStream(keystore);
			final KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(keystoreInputStream, password != null ? password.toCharArray() : null);

			if (keyStore.size() == 0) {
				throw new KeyStoreException(
						"Keystore is empty; while this is legal for keystores in general, APNs clients must have at least one key.");
			}

			final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(algorithm);
			trustManagerFactory.init((KeyStore) null);

			final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(algorithm);
			keyManagerFactory.init(keyStore, password != null ? password.toCharArray() : null);

			sslContext = SSLContext.getInstance(protocol);
			sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
			return sslContext;
		} catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			if (keystoreInputStream != null) {
				try {
					keystoreInputStream.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
