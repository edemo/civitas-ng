package civitas.crypto.signedciphertext;

import civitas.crypto.BasicValuesTestData;

public interface ElGamalSignedCiphertextTestData extends BasicValuesTestData {

	public static final ElGamalSignedCiphertext EL_GAMAL_SIGNED_CIPHERTEXT = new ElGamalSignedCiphertext(
			BIGINT_A, BIGINT_B, BIGINT_C, BIGINT_D);

}
