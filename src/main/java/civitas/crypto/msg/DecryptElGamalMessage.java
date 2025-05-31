package civitas.crypto.msg;

import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.signature.VerifyElGamalSignature;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class DecryptElGamalMessage {

	@Use
	VerifyElGamalSignature verifyElGamalSignature;

	public ElGamalMsg apply(ElGamalPrivateKey key, ElGamalCiphertext ciphertext,
			byte[] additionalEnv) throws CryptoException, CryptoError {
		try {
			ElGamalPrivateKey k = key;
			ElGamalParameters ps = key.params;

			if (ciphertext instanceof ElGamalSignedCiphertext) {
				if (!verifyElGamalSignature.apply(ps,
						(ElGamalSignedCiphertext) ciphertext, additionalEnv)) {
					throw new CryptoException("Ciphertext failed verification");
				}
			}
			ElGamalCiphertext c = ciphertext;
			CivitasBigInteger a = c.a;
			CivitasBigInteger b = c.b;
			CivitasBigInteger m = b.modDivide(a.modPow(k.x, ps.p), ps.p);
			return new ElGamalMsg(m);
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
