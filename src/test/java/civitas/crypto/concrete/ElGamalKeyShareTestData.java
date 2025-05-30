package civitas.crypto.concrete;

public interface ElGamalKeyShareTestData
		extends ProofKnowDiscLogTestData, ElGamalPublicKeyCTestData {
	public static final String EL_GAMAL_KEY_SHARE_XML = "<elGamalKeyShare><pubKey>"
			+ EL_GAMAL_PUBLIC_KEY_E_XML + "</pubKey><proof>"
			+ ELGAMAL_PROOF_KNOWN_DISC_LOG_XML + "</proof></elGamalKeyShare>";
	public static final String EL_GAMAL_KEY_SHARE_NULL_XML = "<elGamalKeyShare><pubKey></pubKey><proof></proof></elGamalKeyShare>";

	public static final ElGamalKeyShareC EL_GAMAL_KEY_SHARE_E = new ElGamalKeyShareC(
			EL_GAMAL_PUBLIC_KEY_E, ELGAMAL_PROOF_KNOWN_DISC_LOG);
	public static final ElGamalKeyShareC EL_GAMAL_KEY_SHARE_EPRIME = new ElGamalKeyShareC(
			EL_GAMAL_PUBLIC_KEY_EPRIME, ELGAMAL_PROOF_KNOWN_DISC_LOG);
	public static final ElGamalKeyShareC EL_GAMAL_KEY_SHARE_NULLPROOF = new ElGamalKeyShareC(
			EL_GAMAL_PUBLIC_KEY_E, null);
	public static final ElGamalKeyShareC EL_GAMAL_KEY_SHARE_NOT_GOOD_PUBKEY_TYPE = new ElGamalKeyShareC(
			EL_GAMAL_PUBLIC_KEY_NULL, ELGAMAL_PROOF_KNOWN_DISC_LOG);

	ElGamalKeyShareC[] KEY_SHARES = new ElGamalKeyShareC[] {
			EL_GAMAL_KEY_SHARE_E,
			EL_GAMAL_KEY_SHARE_EPRIME };
	ElGamalKeyShareC[] KEY_SHARES_WITH_NOT_GOOD_KEY = new ElGamalKeyShareC[] {
			EL_GAMAL_KEY_SHARE_E,
			EL_GAMAL_KEY_SHARE_NOT_GOOD_PUBKEY_TYPE };

}
