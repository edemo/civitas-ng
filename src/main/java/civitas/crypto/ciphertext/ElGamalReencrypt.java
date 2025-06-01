package civitas.crypto.ciphertext;

import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalReencrypt {

	@Use
	GenerateRandomElement generateRandomElement;

	public ElGamalCiphertext apply(ElGamalPublicKey key,
			ElGamalCiphertext ciphertext) throws CryptoError {
		ElGamalParameters ps = key.params;
		CivitasBigInteger c1 = ciphertext.a;
		CivitasBigInteger c2 = ciphertext.b;
		CivitasBigInteger y = generateRandomElement.apply(ps.q);
		c1 = c1.modMultiply(ps.g.modPow(y, ps.p), ps.p);
		c2 = c2.modMultiply(key.y.modPow(y, ps.p), ps.p);
		return new ElGamalCiphertext(c1, c2);
	}

	public ElGamalCiphertext apply(ElGamalPublicKey key,
			ElGamalCiphertext ciphertext, ElGamalReencryptFactor factor)
			throws CryptoError {
		ElGamalParameters ps = key.params;
		CivitasBigInteger a = ciphertext.a;
		CivitasBigInteger b = ciphertext.b;
		CivitasBigInteger r = factor.r;
		a = a.modMultiply(ps.g.modPow(r, ps.p), ps.p);
		b = b.modMultiply(key.y.modPow(r, ps.p), ps.p);
		return new ElGamalCiphertext(a, b);

	}

}
