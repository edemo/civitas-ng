package civitas.crypto.algorithms;

import civitas.crypto.CryptoError;
import civitas.crypto.ElGamalMsg;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.ElGamalReencryptFactor;
import civitas.crypto.ElGamalSignedCiphertext;
import civitas.crypto.concrete.ElGamalMsgC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.crypto.concrete.ElGamalReencryptFactorC;
import civitas.crypto.concrete.ElGamalSignedCiphertextC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class SignAndEncrypt {

	@Use
	GenerateRandomElement generateRandomElement;
	@Use
	CryptoHash cryptoHash;
	@Use
	ElGamalReencrypt elGamalReencrypt;

	public ElGamalSignedCiphertext apply(ElGamalPublicKey key, ElGamalMsg msg,
			ElGamalReencryptFactor r, byte[] additionalEnv) throws CryptoError {
		try {
			ElGamalParametersC ps = (ElGamalParametersC) key.getParams();
			ElGamalPublicKeyC k = (ElGamalPublicKeyC) key;
			CivitasBigInteger m = ((ElGamalMsgC) msg).bigIntValue();
			CivitasBigInteger rr = ((ElGamalReencryptFactorC) r).r;
			CivitasBigInteger s = generateRandomElement.apply(ps.q);
			CivitasBigInteger a = ps.g.modPow(rr, ps.p);
			CivitasBigInteger b = m.modMultiply(k.y.modPow(rr, ps.p), ps.p);

			CivitasBigInteger c = cryptoHash
					.apply(ps.g.modPow(s, ps.p), a, b, additionalEnv).mod(ps.q);
			CivitasBigInteger d = s.modAdd(c.modMultiply(rr, ps.q), ps.q);
			return new ElGamalSignedCiphertextC(a, b, c, d);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

	public ElGamalSignedCiphertext apply(ElGamalPublicKey key, ElGamalMsg msg)
			throws CryptoError {
		return apply(key, msg, elGamalReencrypt.apply(key.getParams()), null);
	}

}
