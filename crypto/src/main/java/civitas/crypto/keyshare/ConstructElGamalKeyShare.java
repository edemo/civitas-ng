package civitas.crypto.keyshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoError;
import civitas.crypto.keypairshare.ElGamalKeyPairShare;
import civitas.crypto.proofknowndisclog.ConstructProofKnowDiscLog;
import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLog;
import civitas.crypto.publickey.ElGamalPublicKey;

@Controller
public class ConstructElGamalKeyShare {
	@Autowired
	ConstructProofKnowDiscLog constructProofKnowDiscLog;
	@Autowired
	VerifyElGamalKeyShare verifyElGamalKeyShare;

	public ElGamalKeyShare apply(ElGamalKeyPairShare kps) throws CryptoError {
		ElGamalKeyShare egks = apply(kps.pubKey,
				constructProofKnowDiscLog.apply(kps.pubKey.params, kps.privKey));
		if (!verifyElGamalKeyShare.apply(egks)) {
			throw new CryptoError("Cannot verify a newly created key share!");
		}
		return egks;
	}

	public ElGamalKeyShare apply(ElGamalPublicKey K,
			ElGamalProofKnowDiscLog proof) throws Error {
		return new ElGamalKeyShare(K, proof);
	}

}
