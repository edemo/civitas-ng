package civitas.crypto.concrete;

import civitas.crypto.algorithms.Constants;

public interface ElGamalReencryptFactorCTestData
		extends BasicValuesTestData, Constants {
	public static final String ELGAMAL_REENCRYPT_FACTOR_XML = "<r>"
			+ BIGINT_A_BASE64 + "</r>";

	public static final ElGamalReencryptFactorC ENCRYPT_FACTOR_ZERO = new ElGamalReencryptFactorC(
			ZERO);

	public static final ElGamalReencryptFactorC ELGAMAL_REENCRYPT_FACTOR_C = new ElGamalReencryptFactorC(
			BIGINT_C);

	public static final ElGamalReencryptFactorC ELGAMAL_REENCRYPT_FACTOR_D = new ElGamalReencryptFactorC(
			BIGINT_D);

	public static final ElGamalReencryptFactorC ELGAMAL_REENCRYPT_FACTOR = new ElGamalReencryptFactorC(
			BIGINT_A);
	public static final String ELGAMAL_REENCRYPT_FACTOR_NULL_XML = "<r></r>";
	public static final ElGamalReencryptFactorC REENCRYPT_FACTOR_RANDOMS_1 = new ElGamalReencryptFactorC(
			RANDOMS_1);

}
