package civitas.common.verifiablevote;

import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.proof1ofl.VerifyElGamal1OfLReencryption;
import civitas.crypto.proofvote.VerifyProofVote;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Use;

public class VerifyVerifiableVote {
	@Use
	VerifyProofVote verifyProofVote;

	@Use
	VerifyElGamal1OfLReencryption verifyElGamal1OfLReencryption;

	public boolean apply(VerifiableVote that, ElGamalPublicKey pubKey,
			CiphertextList ciphertexts, int L) {
		if ((pubKey == null))
			return false;
		return verifyElGamal1OfLReencryption.apply(that.encChoice, pubKey,
				ciphertexts, L)
				&& verifyProofVote.apply(that.proofVote, pubKey.params,
						that.encCapability, that.encChoice.m, that.context);
	}

}
