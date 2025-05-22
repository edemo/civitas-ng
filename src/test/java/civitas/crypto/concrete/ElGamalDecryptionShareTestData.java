package civitas.crypto.concrete;

public interface ElGamalDecryptionShareTestData
		extends ElGamalProofDiscLogEqualityCTestData {

	public static final String EL_GAMAL_DECRYPTION_SHARE_XML = "<elGamalDecryptionShare><ai>"
			+ PUBKEY_VALUE_OTHER_GENERATOR_RANDOM1_FACTOR_POW_A_BASE64 + "</ai>"
			+ EL_GAMAL_PROOF_DISC_LOG_EQUALITY_XML + "</elGamalDecryptionShare>";

	public static final ElGamalDecryptionShareC EL_GAMAL_DECRYPTION_SHARE = new ElGamalDecryptionShareC(
			PUBKEY_VALUE_OTHER_GENERATOR_RANDOM1_FACTOR_POW_A,
			EL_GAMAL_PROOF_DISC_LOG_EQUALITY);

	public static final String EL_GAMAL_DECRYPTION_SHARE_NULL_XML = "<elGamalDecryptionShare><ai></ai></elGamalDecryptionShare>";

}
