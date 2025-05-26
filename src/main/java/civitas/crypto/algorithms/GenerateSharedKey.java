package civitas.crypto.algorithms;

import javax.crypto.SecretKey;

import civitas.crypto.SharedKey;
import civitas.crypto.concrete.SharedKeyC;
import civitas.util.Use;

public class GenerateSharedKey {
	@Use
	GetSharedKeyGenerator getSharedKeyGenerator;

	public SharedKey apply(int keyLength) {
		SecretKey k = getSharedKeyGenerator.apply(keyLength).generateKey();
		return new SharedKeyC(k, "sharedKey-civitas");
	}

}
