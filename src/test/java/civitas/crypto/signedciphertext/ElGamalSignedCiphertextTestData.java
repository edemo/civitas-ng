package civitas.crypto.signedciphertext;

import civitas.crypto.BasicValuesTestData;

public interface ElGamalSignedCiphertextTestData extends BasicValuesTestData {

	public static final ElGamalSignedCiphertextC EL_GAMAL_SIGNED_CIPHERTEXT = new ElGamalSignedCiphertextC(
			BIGINT_A, BIGINT_B, BIGINT_C, BIGINT_D);

}
