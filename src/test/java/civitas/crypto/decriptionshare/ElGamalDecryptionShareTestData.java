package civitas.crypto.decriptionshare;

import civitas.common.Util;
import civitas.crypto.keypairshare.ElGamalKeyPairShareTestData;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityCTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalDecryptionShareTestData
		extends ElGamalProofDiscLogEqualityCTestData, ElGamalKeyPairShareTestData {

	CivitasBigInteger EL_GAMAL_DECRYPTION_SHARE_AI = CIPHERTEXT_E_A
			.modPow(PRIVKEY_E, BIGINT_P);
	public static final ElGamalDecryptionShare EL_GAMAL_DECRYPTION_SHARE = new ElGamalDecryptionShare(
			EL_GAMAL_DECRYPTION_SHARE_AI,
			EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE);

	public static final ElGamalDecryptionShare EL_GAMAL_DECRYPTION_SHARE_BADPROOF = new ElGamalDecryptionShare(
			RANDOMS_1, EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT);
	String EL_GAMAL_DECRYPTION_SHARE_AI_BASE64 = Util
			.fromBigInt(EL_GAMAL_DECRYPTION_SHARE_AI);
	public static final String EL_GAMAL_DECRYPTION_SHARE_XML = "<elGamalDecryptionShare><ai>"
			+ EL_GAMAL_DECRYPTION_SHARE_AI_BASE64 + "</ai>"
			+ EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE_PROOF_XML
			+ "</elGamalDecryptionShare>";

}
