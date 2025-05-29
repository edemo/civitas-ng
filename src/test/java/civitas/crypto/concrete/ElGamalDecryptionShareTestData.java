package civitas.crypto.concrete;

import civitas.common.Util;
import civitas.util.CivitasBigInteger;

public interface ElGamalDecryptionShareTestData
		extends ElGamalProofDiscLogEqualityCTestData, ElGamalCiphertextCTestData {

	CivitasBigInteger EL_GAMAL_DECRYPTION_SHARE_AI = CIPHERTEXT_E_A
			.modPow(PRIVKEY_E, BIGINT_P);
	public static final ElGamalDecryptionShareC EL_GAMAL_DECRYPTION_SHARE = new ElGamalDecryptionShareC(
			EL_GAMAL_DECRYPTION_SHARE_AI,
			EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE);

	public static final ElGamalDecryptionShareC EL_GAMAL_DECRYPTION_SHARE_NULLPROOF = new ElGamalDecryptionShareC(
			RANDOMS_1, null);
	String EL_GAMAL_DECRYPTION_SHARE_AI_BASE64 = Util
			.fromBigInt(EL_GAMAL_DECRYPTION_SHARE_AI);
	public static final String EL_GAMAL_DECRYPTION_SHARE_XML = "<elGamalDecryptionShare><ai>"
			+ EL_GAMAL_DECRYPTION_SHARE_AI_BASE64 + "</ai>"
			+ EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE_PROOF_XML
			+ "</elGamalDecryptionShare>";

	public static final String EL_GAMAL_DECRYPTION_SHARE_NULL_XML = "<elGamalDecryptionShare><ai></ai></elGamalDecryptionShare>";

}
