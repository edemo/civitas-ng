package civitas.crypto.concrete;

import civitas.common.Util;
import civitas.util.CivitasBigInteger;

public interface PETCommitmentCTestData extends BasicValuesTestData {

	public static final String PET_COMMITMENT_HASH_BASE64 = "e6Ilu43SyKyWqm6AJwMX8tUpDBdbssBFqDBwfsrSY7c=";
	public static final CivitasBigInteger PET_COMMITMENT_HASH = Util
			.asBigint(PET_COMMITMENT_HASH_BASE64);
	public static final String PET_C_NULL_XML = "<petC></petC>";
	public static final String PET_C_XML = "<petC>" + PET_COMMITMENT_HASH_BASE64
			+ "</petC>";

	public static final PETCommitmentC PET_COMMITMENT = new PETCommitmentC(
			PET_COMMITMENT_HASH);

	public static final PETCommitmentC PET_COMMITMENT_BAD_HASH = new PETCommitmentC(
			BIGINT_A);

}
