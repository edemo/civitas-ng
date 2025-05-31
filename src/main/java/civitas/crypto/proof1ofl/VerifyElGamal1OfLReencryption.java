package civitas.crypto.proof1ofl;

import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Use;

public class VerifyElGamal1OfLReencryption {

	@Use
	VerifyElGamalProof1OfL verifyElGamalProof1OfLC;

	public boolean apply(ElGamal1OfLReencryption that, ElGamalPublicKey pubKey,
			CiphertextList ciphertexts, int L) {
		if (that.proof == null)
			return false;
		// check the proof is consistent
		return verifyElGamalProof1OfLC.apply(that.proof, pubKey, ciphertexts, L,
				that.m);
	}

}
