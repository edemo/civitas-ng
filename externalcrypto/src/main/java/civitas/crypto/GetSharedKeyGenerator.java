package civitas.crypto;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;

class GetSharedKeyGenerator {
	private Map<String, KeyGenerator> sharedKeyGenerators = new HashMap<>();

	public KeyGenerator apply(int keyLength) {
		String genKey = String.valueOf(keyLength);
		KeyGenerator g = sharedKeyGenerators.get(genKey);
		if (g != null)
			return g;
		try {
			g = KeyGenerator.getInstance(CryptoBase.SHARED_KEY_ALG,
					CryptoBase.SHARED_KEY_PROVIDER);
		} catch (Exception e) {
			throw new Error(e);
		}
		g.init(keyLength);
		sharedKeyGenerators.put(genKey, g);
		return g;
	}
}