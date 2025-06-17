package civitas.common.votercapabilitysharesandproofs;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.proofdvr.ElGamalProofDVR;
import civitas.crypto.proofdvr.VerifyElGamalProofDVR;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.signature.VerifyElGamalSignature;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.crypto.votecapabilityshare.VoteCapabilityShare;
import civitas.util.Use;
import lombok.NonNull;

public class VerifyVoterCapabilitySharesAndProof {

	@Use
	VerifyElGamalSignature verifyElGamalSignature;
	@Use
	CryptoHash cryptoHash;
	@Use
	ElGamalEncrypt elGamalEncrypt;
	@Use
	VerifyElGamalProofDVR verifyElGamalProofDVR;

	public boolean apply(@NonNull VoterCapabilitySharesAndProof that,
			@NonNull ElGamalSignedCiphertext[] postedCapabilities,
			@NonNull ElGamalPublicKey voterPublicKey,
			@NonNull ElGamalPublicKey tabTellerSharedPublicKey, String voterName,
			int tellerIndex) {
		if (that.capabilities.length != that.rencryptFactors.length
				|| that.capabilities.length != that.proofs.length
				|| postedCapabilities.length != that.capabilities.length) {
			return false;
		}

		ElGamalParameters params = tabTellerSharedPublicKey.params;

		for (int i = 0; i < that.capabilities.length; i++) {
			VoteCapabilityShare vc = that.capabilities[i];
			ElGamalReencryptFactor r = that.rencryptFactors[i];
			ElGamalProofDVR p = that.proofs[i];
			// check that p.e equals the posted capability
			if (!p.getE().equals(postedCapabilities[i])) {
				return false;
			}

			// check that the posted capability verifies
			byte[] hash = cryptoHash.apply(tellerIndex + voterName);
			if (!verifyElGamalSignature.apply(params, postedCapabilities[i], hash)) {
				return false;
			}

			// check that p.e' equals enc(vc, ttKey r)
			ElGamalCiphertext encrypted = elGamalEncrypt
					.apply(tabTellerSharedPublicKey, vc, r);
			if (!encrypted.equals(p.getEprime())) {
				return false;
			}

			// check the proof, i.e., that p.e is a reencryption of p.e'
			if (!verifyElGamalProofDVR.apply(p, tabTellerSharedPublicKey,
					voterPublicKey)) {
				return false;
			}
		}
		return true;
	}

}
