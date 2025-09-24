package civitas.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

	public PrivateKey apply(final String storeFile, final String storePassword, final String serverKeyEntry)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
					UnrecoverableKeyException {

		if (null == keyCache.get(serverKeyEntry)) {
			char[] pwdArray = storePassword.toCharArray();
			KeyStore store = KeyStore.getInstance("JKS");
			store.load(Files.newInputStream(Paths.get(storeFile)), pwdArray);
			keyCache.put(serverKeyEntry, (PrivateKey) store.getKey(serverKeyEntry, pwdArray));
		}
		return keyCache.get(serverKeyEntry);
	}
}
