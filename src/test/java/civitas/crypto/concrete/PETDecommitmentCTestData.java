package civitas.crypto.concrete;

import civitas.common.Util;
import civitas.util.CivitasBigInteger;

public interface PETDecommitmentCTestData
		extends ElGamalProofDiscLogEqualityCTestData {

	CivitasBigInteger PET_DECOMMITMENT_D = CIPHERTEXT_E_A
			.modDivide(CIPHERTEXT_EPRIME_A, BIGINT_P).modPow(FACTOR_E, BIGINT_P);
	String PET_DECOMMITMENT_D_BASE64 = Util.fromBigInt(PET_DECOMMITMENT_D);
	CivitasBigInteger PET_DECOMMITMENT_E = CIPHERTEXT_E_B
			.modDivide(CIPHERTEXT_EPRIME_B, BIGINT_P).modPow(FACTOR_E, BIGINT_P);
	String PET_DECOMMITMENT_E_BASE64 = Util.fromBigInt(PET_DECOMMITMENT_E);

	public static final String PET_DECOMMITMENT_XML = "<petD><d>"
			+ PET_DECOMMITMENT_D_BASE64 + "</d><e>" + PET_DECOMMITMENT_E_BASE64
			+ "</e><prf>" + EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_PROOF_XML + "</prf></petD>";

	public static final String PET_DECOMMITMENT_NULL_XML = "<petD><d>" + "</d><e>"
			+ "</e><prf>" + EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_PROOF_XML + "</prf></petD>";

	public static final PETDecommitmentC PET_DECOMMITMENT = new PETDecommitmentC(
			PET_DECOMMITMENT_D, PET_DECOMMITMENT_E, EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT);

}
