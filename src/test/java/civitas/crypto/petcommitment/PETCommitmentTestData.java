package civitas.crypto.petcommitment;

import civitas.common.Util;
import civitas.crypto.BasicValuesTestData;
import civitas.util.CivitasBigInteger;

public interface PETCommitmentTestData extends BasicValuesTestData {

	public static final String PET_COMMITMENT_HASH_BASE64 = "cJwI6pCvSZhmplOrKDGHqSkWeGcSByNlMVR6d0IlbAM=";
	public static final CivitasBigInteger PET_COMMITMENT_HASH = Util
			.asBigint(PET_COMMITMENT_HASH_BASE64);
	public static final String PET_COMMITMENT_NULL_XML = "<petC></petC>";
	public static final String PET_COMMITMENT_XML = "<petC>" + PET_COMMITMENT_HASH_BASE64
			+ "</petC>";

	public static final PETCommitment PET_COMMITMENT = new PETCommitment(
			PET_COMMITMENT_HASH);

	public static final PETCommitment PET_COMMITMENT_BAD_HASH = new PETCommitment(
			BIGINT_A);

}
