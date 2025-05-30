package civitas.crypto.signedciphertext;

import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.msg.ElGamalMsgC;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickey.ElGamalPublicKeyC;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorC;
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

	public ElGamalSignedCiphertext apply(ElGamalPublicKey key, ElGamalMsg msg,
			ElGamalReencryptFactor r) throws CryptoError {
		return apply(key, msg, r, null);
	}

}
