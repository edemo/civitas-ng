package civitas.crypto.algorithms;

import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalMsg;
import civitas.crypto.ElGamalPrivateKey;
import civitas.crypto.ElGamalSignedCiphertext;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalMsgC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalPrivateKeyC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class DecryptElGamalMessage {

	@Use
	VerifyElGamalSignature verifyElGamalSignature;

	public ElGamalMsg apply(ElGamalPrivateKey key, ElGamalCiphertext ciphertext,
			byte[] additionalEnv) throws CryptoException, CryptoError {
		try {
			ElGamalPrivateKeyC k = (ElGamalPrivateKeyC) key;
			ElGamalParametersC ps = (ElGamalParametersC) key.getParams();

			if (ciphertext instanceof ElGamalSignedCiphertext) {
				if (!verifyElGamalSignature.apply(ps,
						(ElGamalSignedCiphertext) ciphertext, additionalEnv)) {
					throw new CryptoException("Ciphertext failed verification");
				}
			}
			ElGamalCiphertextC c = (ElGamalCiphertextC) ciphertext;
			CivitasBigInteger a = c.a;
			CivitasBigInteger b = c.b;
			CivitasBigInteger m = b.modDivide(a.modPow(k.x, ps.p), ps.p);
			return new ElGamalMsgC(m);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		} catch (NullPointerException e) {
			throw new CryptoError(e);
		}
	}

	public ElGamalMsg apply(ElGamalPrivateKey key, ElGamalCiphertext ciphertext)
			throws CryptoException, CryptoError {
		return apply(key, ciphertext, null);
	}

}
