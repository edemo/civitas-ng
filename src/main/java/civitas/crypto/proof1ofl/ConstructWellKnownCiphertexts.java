package civitas.crypto.proof1ofl;

import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.algorithms.Constants;
import civitas.crypto.algorithms.ElGamalEncrypt;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.msg.ElGamalMsgC;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorC;
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
