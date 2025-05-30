package civitas.crypto.keyshare;

import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLogC;
import civitas.crypto.proofknowndisclog.VerifyElGamalProofKnowDiscLog;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Use;

public class VerifyElGamalKeyShare {

	@Use
	VerifyElGamalProofKnowDiscLog verifyElGamalProofKnowDiscLog;

	public boolean apply(ElGamalKeyShare that) {
		ElGamalProofKnowDiscLogC prf = (ElGamalProofKnowDiscLogC) that.proof;
		// the base of the prf is correct, as it is taken from params.
		ElGamalPublicKey K = that.pubKey;
		if (prf == null || K == null) {
			return false;
		}
		return prf.v.equals(K.y)
				&& verifyElGamalProofKnowDiscLog.apply(prf, that.pubKey.params);
	}

}
