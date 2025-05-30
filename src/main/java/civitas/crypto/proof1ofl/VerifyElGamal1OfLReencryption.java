package civitas.crypto.proof1ofl;

import civitas.common.CiphertextList;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionC;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Use;

public class VerifyElGamal1OfLReencryption {

	@Use
	VerifyElGamalProof1OfLC verifyElGamalProof1OfLC;

	public boolean apply(ElGamal1OfLReencryptionC that, ElGamalPublicKey pubKey,
			CiphertextList ciphertexts, int L) {
		if (that.proof == null)
			return false;
		// check the proof is consistent
		return verifyElGamalProof1OfLC.apply(that.proof, pubKey, ciphertexts, L,
				that.m);
	}

}
