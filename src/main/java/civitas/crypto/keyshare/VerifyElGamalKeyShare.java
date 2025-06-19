package civitas.crypto.keyshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLog;
import civitas.crypto.proofknowndisclog.VerifyElGamalProofKnowDiscLog;
import civitas.crypto.publickey.ElGamalPublicKey;

@Service
public class VerifyElGamalKeyShare {

	@Autowired
	VerifyElGamalProofKnowDiscLog verifyElGamalProofKnowDiscLog;

	public boolean apply(ElGamalKeyShare that) {
		ElGamalProofKnowDiscLog prf = that.proof;
		ElGamalPublicKey K = that.pubKey;
		return prf.v.equals(K.y)
				&& verifyElGamalProofKnowDiscLog.apply(prf, that.pubKey.params);
	}

}
