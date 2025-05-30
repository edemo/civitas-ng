package civitas.crypto.algorithms;

import civitas.crypto.concrete.ElGamalKeyShareC;
import civitas.crypto.concrete.ElGamalProofKnowDiscLogC;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.util.Use;

public class VerifyElGamalKeyShare {

	@Use
	VerifyElGamalProofKnowDiscLog verifyElGamalProofKnowDiscLog;

	public boolean apply(ElGamalKeyShareC that) {
		ElGamalProofKnowDiscLogC prf = (ElGamalProofKnowDiscLogC) that.proof;
		// the base of the prf is correct, as it is taken from params.
		ElGamalPublicKeyC K = (ElGamalPublicKeyC) that.pubKey;
		if (prf == null || K == null) {
			return false;
		}
		return prf.v.equals(K.y)
				&& verifyElGamalProofKnowDiscLog.apply(prf, that.pubKey.getParams());
	}

}
