package civitas.crypto.decriptionshare;

import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalDecryptionShareTestData
		extends ElGamalProofDiscLogEqualityTestData {

	CivitasBigInteger EL_GAMAL_DECRYPTION_SHARE_AI = CIPHERTEXT_E_A
			.modPow(PRIVKEY_E, BIGINT_P);
	public static final ElGamalDecryptionShare EL_GAMAL_DECRYPTION_SHARE = new ElGamalDecryptionShare(
			EL_GAMAL_DECRYPTION_SHARE_AI,
			EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE);

	public static final ElGamalDecryptionShare EL_GAMAL_DECRYPTION_SHARE_BAD_AI = new ElGamalDecryptionShare(
			BIGINT_A, EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE);

	public static final ElGamalDecryptionShare EL_GAMAL_DECRYPTION_SHARE_BADPROOF = new ElGamalDecryptionShare(
			RANDOMS_1, EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT);

}
