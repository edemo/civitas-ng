package civitas.crypto.concrete;

public interface PETShareCTestData
		extends BasicValuesTestData, ElGamalCiphertextCTestData {

	public static final PETShareC PET_SHARE_C = new PETShareC(
			EL_GAMAL_CIPHERTEXT_A, EL_GAMAL_CIPHERTEXT_B, BIGINT_C);

	public static final String PET_SHARE_XML = "<petShare>"
			+ EL_GAMAL_CIPHERTEXT_A_XML + EL_GAMAL_CIPHERTEXT_B_XML + "<exponent>"
			+ BIGINT_C_BASE64 + "</exponent></petShare>";
	public static final String PET_SHARE_NULL_XML = "<petShare></petShare>";

}
