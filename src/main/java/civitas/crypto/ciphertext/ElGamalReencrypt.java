package civitas.crypto.ciphertext;

import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalReencrypt {

	@Use
	GenerateRandomElement generateRandomElement;

	public ElGamalCiphertext apply(ElGamalPublicKey key,
			ElGamalCiphertext ciphertext) throws CryptoError {
		try {
			ElGamalParameters ps = key.params;
			ElGamalPublicKey k = key;
			ElGamalCiphertext c = ciphertext;
			CivitasBigInteger c1 = c.a;
			CivitasBigInteger c2 = c.b;
			CivitasBigInteger y = generateRandomElement.apply(ps.q);
			c1 = c1.modMultiply(ps.g.modPow(y, ps.p), ps.p);
			c2 = c2.modMultiply(k.y.modPow(y, ps.p), ps.p);
			return new ElGamalCiphertext(c1, c2);
		} catch (ClassCastException impossible) {
			throw new CryptoError(impossible);
		}
	}

	public ElGamalReencryptFactor apply(ElGamalParameters params)
			throws CryptoError {
		try {
			ElGamalParameters ps = params;
			return new ElGamalReencryptFactor(generateRandomElement.apply(ps.q));
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	public ElGamalCiphertext apply(ElGamalPublicKey key,
			ElGamalCiphertext ciphertext, ElGamalReencryptFactor factor)
			throws CryptoError {
		try {
			ElGamalParameters ps = key.params;
			ElGamalPublicKey k = key;
			ElGamalCiphertext c = ciphertext;
			CivitasBigInteger a = c.a;
			CivitasBigInteger b = c.b;
			CivitasBigInteger r = ((ElGamalReencryptFactor) factor).r;
			a = a.modMultiply(ps.g.modPow(r, ps.p), ps.p);
			b = b.modMultiply(k.y.modPow(r, ps.p), ps.p);
			return new ElGamalCiphertext(a, b);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}

	}

}
