package civitas.crypto.algorithms;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;

public class GetSharedKeyGenerator implements Constants {

	private Map<String, KeyGenerator> sharedKeyGenerators = new HashMap<String, KeyGenerator>();

	/**
	 * Get an appropriate shared key generator, creating one if necessary.
	 */
	public KeyGenerator apply(int keyLength) {
		String genKey = String.valueOf(keyLength);
		KeyGenerator g = sharedKeyGenerators.get(genKey);
		if (g != null)
			return g;
		try {
			g = KeyGenerator.getInstance(SHARED_KEY_ALG, SHARED_KEY_PROVIDER);
		} catch (Exception e) {
			throw new Error(e);
		}
		g.init(keyLength);
		sharedKeyGenerators.put(genKey, g);
		return g;
	}

}
