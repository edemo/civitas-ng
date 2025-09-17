package civitas.crypto.petcommitment;

import civitas.common.CommonUtil;
import civitas.crypto.ciphertext.ElGamalCiphertextTestData;
import civitas.util.CivitasBigInteger;

public interface PETCommitmentTestData extends ElGamalCiphertextTestData {

	CivitasBigInteger PET_COMMITMENT_DI = CIPHERTEXT_E_A
			.modDivide(CIPHERTEXT_EPRIME_A, BIGINT_P)
			.modPow(FACTOR_E, EL_GAMAL_PARAMETERS.p);
	CivitasBigInteger PET_COMMITMENT_EI = CIPHERTEXT_E_B
			.modDivide(CIPHERTEXT_EPRIME_B, BIGINT_P).modPow(FACTOR_E, BIGINT_P);

	CivitasBigInteger PET_COMMITMENT_HASH = CommonUtil
			.asBigint("cJwI6pCvSZhmplOrKDGHqSkWeGcSByNlMVR6d0IlbAM=");

	public static final PETCommitment PET_COMMITMENT = new PETCommitment(
			PET_COMMITMENT_HASH);

	public static final PETCommitment PET_COMMITMENT_BAD_HASH = new PETCommitment(
			BIGINT_A);

}
