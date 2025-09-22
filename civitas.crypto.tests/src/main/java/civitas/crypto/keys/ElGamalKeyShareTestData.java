package civitas.crypto.keys;

import civitas.crypto.keyshare.ElGamalKeyShare;
import civitas.crypto.proofknowndisclog.ProofKnowDiscLogTestData;
import civitas.crypto.publickey.ElGamalPublicKeyTestData;

public interface ElGamalKeyShareTestData extends ProofKnowDiscLogTestData, ElGamalPublicKeyTestData {

	ElGamalKeyShare EL_GAMAL_KEY_SHARE_E = new ElGamalKeyShare(EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PROOF_KNOWN_DISC_LOG);
	ElGamalKeyShare EL_GAMAL_KEY_SHARE_EPRIME =
			new ElGamalKeyShare(EL_GAMAL_PUBLIC_KEY_EPRIME, EL_GAMAL_PROOF_KNOWN_DISC_LOG);
	ElGamalKeyShare EL_GAMAL_KEY_SHARE_NOT_GOOD_PUBKEY_TYPE =
			new ElGamalKeyShare(EL_GAMAL_PUBLIC_KEY_A_USING_G_OTHER, EL_GAMAL_PROOF_KNOWN_DISC_LOG);

	ElGamalKeyShare[] KEY_SHARES = {EL_GAMAL_KEY_SHARE_E, EL_GAMAL_KEY_SHARE_EPRIME};
	ElGamalKeyShare[] KEY_SHARES_WITH_NOT_GOOD_KEY = {EL_GAMAL_KEY_SHARE_E, EL_GAMAL_KEY_SHARE_NOT_GOOD_PUBKEY_TYPE};
}
