package civitas.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GetPublicKey {
	@Autowired
	Configuration configuration;

	Map<String, PublicKey> cache = new HashMap<>();

	public PublicKey apply(String storeFile, String storePassword, String serverKeyEntry)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		if (null == cache.get(serverKeyEntry)) {
			char[] pwdArray = storePassword.toCharArray();
			KeyStore store = KeyStore.getInstance("JKS");
			store.load(Files.newInputStream(Paths.get(storeFile)), pwdArray);
			cache.put(serverKeyEntry, store.getCertificate(serverKeyEntry).getPublicKey());
		}
		return cache.get(serverKeyEntry);
	}
}
