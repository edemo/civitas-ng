package civitas.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;

class GetSharedKeyGenerator {
	private final Map<String, KeyGenerator> sharedKeyGenerators = new HashMap<>();

	public KeyGenerator apply(int keyLength) {
		String genKey = String.valueOf(keyLength);
		KeyGenerator g = sharedKeyGenerators.get(genKey);
		if (g != null) {
			return g;
		}
		try {
			g = KeyGenerator.getInstance(Constants.SHARED_KEY_ALG,
					Constants.SHARED_KEY_PROVIDER);
		} catch (NoSuchAlgorithmException | NoSuchProviderException impossible) {
			throw new CryptoError(impossible);
		}

		g.init(keyLength);
		sharedKeyGenerators.put(genKey, g);
		return g;
	}
}
