package civitas.crypto.ciphertext;

import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.msg.ElGamalMsgC;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalEncrypt {
	@Use
	private static GenerateRandomElement generateRandomElement;

	public ElGamalCiphertext apply(ElGamalPublicKey key, ElGamalMsg msg)
			throws CryptoError {
		ElGamalParametersC ps = (ElGamalParametersC) key.params;
		ElGamalPublicKey k = key;
		CivitasBigInteger m = ((ElGamalMsgC) msg).bigIntValue();
		CivitasBigInteger r = generateRandomElement.apply(ps.q);
		CivitasBigInteger a = ps.g.modPow(r, ps.p);
		CivitasBigInteger b = m.modMultiply(k.y.modPow(r, ps.p), ps.p);
		return new ElGamalCiphertext(a, b);
	}

	public ElGamalCiphertext apply(ElGamalPublicKey key, ElGamalMsg msg,
			ElGamalReencryptFactor encryptFactor) throws CryptoError {
		ElGamalParametersC ps = (ElGamalParametersC) key.params;
		ElGamalPublicKey k = key;
		CivitasBigInteger r = ((ElGamalReencryptFactorC) encryptFactor).r;
		CivitasBigInteger m = ((ElGamalMsgC) msg).bigIntValue();
		CivitasBigInteger a = ps.g.modPow(r, ps.p);
		CivitasBigInteger s = k.y.modPow(r, ps.p);
		CivitasBigInteger b = m.modMultiply(s, ps.p);
		return new ElGamalCiphertext(a, b);
	}

}
