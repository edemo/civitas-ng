package civitas.crypto.decriptionshare.tests;

import civitas.crypto.decriptionshare.ElGamalDecryptionShare;
import civitas.crypto.proofdisclog.tests.ElGamalProofDiscLogEqualityTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalDecryptionShareTestData extends ElGamalProofDiscLogEqualityTestData {

	CivitasBigInteger EL_GAMAL_DECRYPTION_SHARE_AI = CIPHERTEXT_E_A.modPow(PRIVKEY_E, BIGINT_P);
	ElGamalDecryptionShare EL_GAMAL_DECRYPTION_SHARE =
			new ElGamalDecryptionShare(EL_GAMAL_DECRYPTION_SHARE_AI, EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE);

	ElGamalDecryptionShare EL_GAMAL_DECRYPTION_SHARE_BAD_AI =
			new ElGamalDecryptionShare(BIGINT_A, EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE);

	ElGamalDecryptionShare EL_GAMAL_DECRYPTION_SHARE_BADPROOF = new ElGamalDecryptionShare(
			EL_GAMAL_DECRYPTION_SHARE_AI, EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE_BAD_B);

	ElGamalDecryptionShare[] EL_GAMAL_DECRYPTION_SHARES = {EL_GAMAL_DECRYPTION_SHARE, EL_GAMAL_DECRYPTION_SHARE_BADPROOF
	};
}
