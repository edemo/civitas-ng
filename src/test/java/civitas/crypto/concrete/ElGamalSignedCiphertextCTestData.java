package civitas.crypto.concrete;

public interface ElGamalSignedCiphertextCTestData extends ConcreteTestData {
	public static final ElGamalSignedCiphertextC EL_GAMAL_SIGNED_CIPHERTEXT = new ElGamalSignedCiphertextC(
			BIGINT_A, BIGINT_B, BIGINT_C, BIGINT_D);
	public static final String EL_GAMAL_SIGNED_CIPHERTEXT_XML = "<elGamalSignedCiphertext><a>"
			+ BIGINT_A_BASE64 + "</a><b>" + BIGINT_B_BASE64 + "</b><c>"
			+ BIGINT_C_BASE64 + "</c><d>" + BIGINT_D_BASE64
			+ "</d></elGamalSignedCiphertext>";
	public static final String EL_GAMAL_SIGNED_CIPHERTEXT_NULL_XML = "<elGamalSignedCiphertext><a>"
			+ "</a><b>" + "</b><c>" + "</c><d>" + "</d></elGamalSignedCiphertext>";

}
