package civitas.crypto.algorithms;

import civitas.common.CiphertextList;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.concrete.ElGamal1OfLReencryptionC;
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
