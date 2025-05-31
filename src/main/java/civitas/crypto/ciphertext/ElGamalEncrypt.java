package civitas.crypto.ciphertext;

import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalEncrypt {
	@Use
	private static GenerateRandomElement generateRandomElement;

	public ElGamalCiphertext apply(ElGamalPublicKey key, ElGamalMsg msg)
			throws CryptoError {
		ElGamalParameters ps = key.params;
		ElGamalPublicKey k = key;
		CivitasBigInteger m = msg.m;
		CivitasBigInteger r = generateRandomElement.apply(ps.q);
		CivitasBigInteger a = ps.g.modPow(r, ps.p);
		CivitasBigInteger b = m.modMultiply(k.y.modPow(r, ps.p), ps.p);
		return new ElGamalCiphertext(a, b);
	}

	public ElGamalCiphertext apply(ElGamalPublicKey key, ElGamalMsg msg,
			ElGamalReencryptFactor encryptFactor) throws CryptoError {
		ElGamalParameters ps = key.params;
		ElGamalPublicKey k = key;
		CivitasBigInteger r = encryptFactor.r;
		CivitasBigInteger m = msg.m;
		CivitasBigInteger a = ps.g.modPow(r, ps.p);
		CivitasBigInteger s = k.y.modPow(r, ps.p);
		CivitasBigInteger b = m.modMultiply(s, ps.p);
		return new ElGamalCiphertext(a, b);
	}

}
