package civitas.crypto.algorithms;

import civitas.crypto.CryptoError;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalMsg;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.ElGamalReencryptFactor;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalMsgC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.crypto.concrete.ElGamalReencryptFactorC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalEncrypt {
	@Use
	private static GenerateRandomElement generateRandomElement;

	public ElGamalCiphertext apply(ElGamalPublicKey key, ElGamalMsg msg)
			throws CryptoError {
		try {
			ElGamalParametersC ps = (ElGamalParametersC) key.getParams();
			ElGamalPublicKeyC k = (ElGamalPublicKeyC) key;
			CivitasBigInteger m = ((ElGamalMsgC) msg).bigIntValue();
			CivitasBigInteger r = generateRandomElement.apply(ps.q);
			CivitasBigInteger a = ps.g.modPow(r, ps.p);
			CivitasBigInteger b = m.modMultiply(k.y.modPow(r, ps.p), ps.p);
			return new ElGamalCiphertextC(a, b);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	public ElGamalCiphertext apply(ElGamalPublicKey key, ElGamalMsg msg,
			ElGamalReencryptFactor encryptFactor) throws CryptoError {
		try {
			ElGamalParametersC ps = (ElGamalParametersC) key.getParams();
			ElGamalPublicKeyC k = (ElGamalPublicKeyC) key;
			CivitasBigInteger r = ((ElGamalReencryptFactorC) encryptFactor).r;
			CivitasBigInteger m = ((ElGamalMsgC) msg).bigIntValue();
			CivitasBigInteger a = ps.g.modPow(r, ps.p);
			CivitasBigInteger b = m.modMultiply(k.y.modPow(r, ps.p), ps.p);
			return new ElGamalCiphertextC(a, b);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

}
