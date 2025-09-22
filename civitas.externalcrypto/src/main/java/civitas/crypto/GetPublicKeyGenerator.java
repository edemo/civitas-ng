package civitas.crypto;

import java.security.KeyPairGenerator;
import java.util.HashMap;
import java.util.Map;

class GetPublicKeyGenerator implements Constants {
	private final Map<String, KeyPairGenerator> publicKeyGenerators = new HashMap<>();

	public KeyPairGenerator apply(int keyLength) {
		String genKey = String.valueOf(keyLength);
		KeyPairGenerator g = publicKeyGenerators.get(genKey);
		if (g != null) {
			return g;
		}
		try {
			g = KeyPairGenerator.getInstance(PUBLIC_KEY_ALG, PUBLIC_KEY_PROVIDER);
			g.initialize(keyLength);
			publicKeyGenerators.put(genKey, g);
			return g;
		} catch (Exception impossible) {
			throw new CryptoError(impossible);
		}
	}

}
