package civitas.crypto.keyshare;

import civitas.crypto.CryptoError;
import civitas.crypto.keypairshare.ElGamalKeyPairShare;
import civitas.crypto.proofknowndisclog.ConstructProofKnowDiscLog;
import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLog;
import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLogC;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Use;

public class ConstructElGamalKeyShare {
	@Use
	ConstructProofKnowDiscLog constructProofKnowDiscLog;
	@Use
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
		if (K instanceof ElGamalPublicKey
				&& proof instanceof ElGamalProofKnowDiscLogC) {
			return new ElGamalKeyShare(K, proof);
		}
		throw new CryptoError("problem with parameters");
	}

}
