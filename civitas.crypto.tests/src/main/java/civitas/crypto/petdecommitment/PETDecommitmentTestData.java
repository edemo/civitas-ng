package civitas.crypto.petdecommitment;

import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityTestData;
import civitas.util.CivitasBigInteger;

public interface PETDecommitmentTestData
		extends ElGamalProofDiscLogEqualityTestData {

	CivitasBigInteger PET_DECOMMITMENT_D = CIPHERTEXT_E_A
			.modDivide(CIPHERTEXT_EPRIME_A, BIGINT_P).modPow(FACTOR_E, BIGINT_P);
	CivitasBigInteger PET_DECOMMITMENT_E = CIPHERTEXT_E_B
			.modDivide(CIPHERTEXT_EPRIME_B, BIGINT_P).modPow(FACTOR_E, BIGINT_P);

	public static final PETDecommitment PET_DECOMMITMENT = new PETDecommitment(
			PET_DECOMMITMENT_D, PET_DECOMMITMENT_E,
			EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT);
	public static final PETDecommitment PET_DECOMMITMENT2 = new PETDecommitment(
			BIGINT_A, BIGINT_B, EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT);

	PETDecommitment[] PET_DECOMMITMENTS = new PETDecommitment[] {
			PET_DECOMMITMENT,
			PET_DECOMMITMENT2 };

}
