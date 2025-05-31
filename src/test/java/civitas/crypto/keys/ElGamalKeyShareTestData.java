package civitas.crypto.keys;

import civitas.crypto.keyshare.ElGamalKeyShare;
import civitas.crypto.proofknowndisclog.ProofKnowDiscLogTestData;
import civitas.crypto.publickey.ElGamalPublicKeyCTestData;

public interface ElGamalKeyShareTestData
		extends ProofKnowDiscLogTestData, ElGamalPublicKeyCTestData {
	public static final String EL_GAMAL_KEY_SHARE_XML = "<elGamalKeyShare><pubKey>"
			+ EL_GAMAL_PUBLIC_KEY_E_XML + "</pubKey><proof>"
			+ EL_GAMAL_PROOF_KNOWN_DISC_LOG_XML + "</proof></elGamalKeyShare>";

	public static final ElGamalKeyShare EL_GAMAL_KEY_SHARE_E = new ElGamalKeyShare(
			EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PROOF_KNOWN_DISC_LOG);
	public static final ElGamalKeyShare EL_GAMAL_KEY_SHARE_EPRIME = new ElGamalKeyShare(
			EL_GAMAL_PUBLIC_KEY_EPRIME, EL_GAMAL_PROOF_KNOWN_DISC_LOG);
	public static final ElGamalKeyShare EL_GAMAL_KEY_SHARE_NOT_GOOD_PUBKEY_TYPE = new ElGamalKeyShare(
			EL_GAMAL_PUBLIC_KEY_A_USING_G_OTHER, EL_GAMAL_PROOF_KNOWN_DISC_LOG);

	ElGamalKeyShare[] KEY_SHARES = new ElGamalKeyShare[] {
			EL_GAMAL_KEY_SHARE_E,
			EL_GAMAL_KEY_SHARE_EPRIME };
	ElGamalKeyShare[] KEY_SHARES_WITH_NOT_GOOD_KEY = new ElGamalKeyShare[] {
			EL_GAMAL_KEY_SHARE_E,
			EL_GAMAL_KEY_SHARE_NOT_GOOD_PUBKEY_TYPE };

}
