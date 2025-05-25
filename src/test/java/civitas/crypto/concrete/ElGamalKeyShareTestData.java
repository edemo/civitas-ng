package civitas.crypto.concrete;

public interface ElGamalKeyShareTestData
		extends ProofKnowDiscLogTestData, ElGamalPublicKeyCTestData {
	public static final String EL_GAMAL_KEY_SHARE_XML = "<elGamalKeyShare><pubKey>"
			+ EL_GAMAL_PUBLIC_KEY_XML + "</pubKey><proof>"
			+ ELGAMAL_PROOF_KNOWN_DISC_LOG_XML + "</proof></elGamalKeyShare>";
	public static final String EL_GAMAL_KEY_SHARE_NULL_XML = "<elGamalKeyShare><pubKey></pubKey><proof></proof></elGamalKeyShare>";

	public static final ElGamalKeyShareC EL_GAMAL_KEY_SHARE_C = new ElGamalKeyShareC(
			EL_GAMAL_PUBLIC_KEY, ELGAMAL_PROOF_KNOWN_DISC_LOG);

}
