package civitas.crypto.algorithms;

import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.ElGamalReencryptFactor;
import civitas.crypto.concrete.ElGamalMsgC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalReencryptFactorC;
import civitas.util.Use;

public class ConstructWellKnownCiphertexts implements Constants {

	@Use
	ElGamalEncrypt elGamalEncrypt;

	public ElGamalCiphertext[] apply(ElGamalPublicKey key, int count)
			throws CryptoError {
		if (count < 0 || key == null)
			return null;
		ElGamalCiphertext[] cs = new ElGamalCiphertext[count];

		// Note: the well known ciphertexts MUST be the encryptions of 1,2,3,...
		// using the encryption factor 0. This is assumed by some of the
		// zero knowledge proofs.
		ElGamalReencryptFactor factor = new ElGamalReencryptFactorC(ZERO);
		try {
			ElGamalParametersC params = (ElGamalParametersC) key.getParams();
			for (int i = 0; i < count; i++) {
				// encrypt (i+1);
				try {
					cs[i] = elGamalEncrypt.apply(key, new ElGamalMsgC(i + 1, params),
							factor);
				} catch (CryptoException imposs) {
					throw new CryptoError(imposs);
				}
			}
		} catch (ClassCastException e) {
			return null;
		}
		return cs;
	}

}
