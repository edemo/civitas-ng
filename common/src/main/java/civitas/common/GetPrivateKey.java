package civitas.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GetPrivateKey {

	@Autowired
	Configuration configuration;

	Map<String, PrivateKey> keyCache = new HashMap<>();

	public PrivateKey apply(String storeFile, String storePassword, String serverKeyEntry) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {

		if (null == keyCache.get(serverKeyEntry)) {
			char[] pwdArray = storePassword.toCharArray();
			KeyStore store = KeyStore.getInstance("JKS");
			store.load(new FileInputStream(storeFile), pwdArray);
			keyCache.put(serverKeyEntry, (PrivateKey) store.getKey(serverKeyEntry, pwdArray));
		}
		return keyCache.get(serverKeyEntry);
	}
}
