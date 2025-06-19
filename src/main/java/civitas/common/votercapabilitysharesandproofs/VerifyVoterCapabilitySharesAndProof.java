package civitas.common.votercapabilitysharesandproofs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import lombok.NonNull;

@Service
public class VerifyVoterCapabilitySharesAndProof {

	@Autowired
	VerifyElGamalSignature verifyElGamalSignature;
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	ElGamalEncrypt elGamalEncrypt;
	@Autowired
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
		byte[] hash = cryptoHash.apply(tellerIndex + voterName);

		for (int i = 0; i < that.capabilities.length; i++) {
			VoteCapabilityShare vc = that.capabilities[i];
			ElGamalReencryptFactor r = that.rencryptFactors[i];
			ElGamalProofDVR p = that.proofs[i];
			if (!p.getE().equals(postedCapabilities[i])) {
				return false;
			}

			if (!verifyElGamalSignature.apply(params, postedCapabilities[i], hash)) {
				return false;
			}

			ElGamalCiphertext encrypted = elGamalEncrypt
					.apply(tabTellerSharedPublicKey, vc, r);
			if (!encrypted.equals(p.getEprime()))
				return false;
			if (!verifyElGamalProofDVR.apply(p, tabTellerSharedPublicKey,
					voterPublicKey)) {
				return false;
			}
		}
		return true;
	}

}
