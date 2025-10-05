package civitas.common.votercapabilitysharesandproofs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
import jakarta.annotation.Nonnull;

@Controller
public class VerifyVoterCapabilitySharesAndProof {

	@Autowired
	VerifyElGamalSignature verifyElGamalSignature;

	@Autowired
	CryptoHash cryptoHash;

	@Autowired
	ElGamalEncrypt elGamalEncrypt;

	@Autowired
	VerifyElGamalProofDVR verifyElGamalProofDVR;

	public boolean apply(
			@Nonnull final VoterCapabilitySharesAndProof that,
			@Nonnull final ElGamalSignedCiphertext[] postedCapabilities,
			@Nonnull final ElGamalPublicKey voterPublicKey,
			@Nonnull final ElGamalPublicKey tabTellerSharedPublicKey,
			final String voterName,
			final int tellerIndex) {
		if (null == voterPublicKey) {
			throw new NullPointerException();
		}
		if (that.capabilities.length != that.rencryptFactors.length
				|| that.capabilities.length != that.proofs.length
				|| postedCapabilities.length != that.capabilities.length) {
			return false;
		}

		ElGamalParameters params = tabTellerSharedPublicKey.params;
		byte[] hash = cryptoHash.apply((tellerIndex + voterName).getBytes());

		for (int i = 0; i < that.capabilities.length; i++) {
			VoteCapabilityShare vc = that.capabilities[i];
			ElGamalReencryptFactor r = that.rencryptFactors[i];
			ElGamalProofDVR p = that.proofs[i];
			if (!p.e().equals(postedCapabilities[i])
					|| !verifyElGamalSignature.apply(params, postedCapabilities[i], hash)) {
				return false;
			}

			ElGamalCiphertext encrypted = elGamalEncrypt.apply(tabTellerSharedPublicKey, vc, r);
			if (!encrypted.equals(p.eprime())
					|| !verifyElGamalProofDVR.apply(p, tabTellerSharedPublicKey, voterPublicKey)) {
				return false;
			}
		}
		return true;
	}
}
