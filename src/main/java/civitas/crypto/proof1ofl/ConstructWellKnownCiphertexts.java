package civitas.crypto.proof1ofl;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.msg.EncodeMessage;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.Use;

public class ConstructWellKnownCiphertexts implements Constants {

	@Use
	ElGamalEncrypt elGamalEncrypt;
	@Use
	EncodeMessage encodeMessage;

	public CiphertextList apply(ElGamalPublicKey key, int count)
			throws CryptoError {
		if (count < 0 || key == null)
			return null;
		CiphertextList cs = new CiphertextList();

		// Note: the well known ciphertexts MUST be the encryptions of 1,2,3,...
		// using the encryption factor 0. This is assumed by some of the
		// zero knowledge proofs.
		ElGamalReencryptFactor factor = new ElGamalReencryptFactor(ZERO);
		try {
			ElGamalParameters params = key.params;
			for (int i = 0; i < count; i++) {
				// encrypt (i+1);
				try {
					cs.add(elGamalEncrypt.apply(key,
							new ElGamalMsg(encodeMessage.apply(i + 1, params)), factor));
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
