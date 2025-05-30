package civitas.crypto.algorithms;

import java.security.KeyPairGenerator;
import java.util.HashMap;
import java.util.Map;

import civitas.crypto.Constants;

public class GetPublicKeyGenerator implements Constants {

	private Map<String, KeyPairGenerator> publicKeyGenerators = new HashMap<>();

	/**
	 * Get an appropriate public key generator, creating one if necessary.
	 */
	public KeyPairGenerator apply(int keyLength) {
		String genKey = String.valueOf(keyLength);
		KeyPairGenerator g = publicKeyGenerators.get(genKey);
		if (g != null)
			return g;
		// need to create the public key generator
		try {
			g = KeyPairGenerator.getInstance(PUBLIC_KEY_ALG, PUBLIC_KEY_PROVIDER);
			g.initialize(keyLength);
			publicKeyGenerators.put(genKey, g);
			return g;
		} catch (Exception impossible) {
			throw new Error(impossible);
		}
	}

}
