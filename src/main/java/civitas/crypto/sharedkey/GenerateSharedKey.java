package civitas.crypto.sharedkey;

import javax.crypto.SecretKey;

import civitas.util.Use;

public class GenerateSharedKey {
	@Use
	GetSharedKeyGenerator getSharedKeyGenerator;

	public SharedKey apply(int keyLength) {
		SecretKey k = getSharedKeyGenerator.apply(keyLength).generateKey();
		return new SharedKeyC(k, "sharedKey-civitas");
	}

}
