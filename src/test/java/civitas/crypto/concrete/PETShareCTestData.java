package civitas.crypto.concrete;

public interface PETShareCTestData
		extends BasicValuesTestData, ElGamalCiphertextCTestData {

	public static final PETShareC PET_SHARE_C = new PETShareC(CIPHERTEXT_E,
			CIPHERTEXT_EPRIME, FACTOR_E);

	public static final String PET_SHARE_XML = "<petShare>"
			+ EL_GAMAL_CIPHERTEXT_E_XML + EL_GAMAL_CIPHERTEXT_EPRIME_XML
			+ "<exponent>" + FACTOR_E_BASE64 + "</exponent></petShare>";
	public static final String PET_SHARE_NULL_XML = "<petShare></petShare>";

}
