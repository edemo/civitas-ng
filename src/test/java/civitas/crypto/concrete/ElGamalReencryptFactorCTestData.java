package civitas.crypto.concrete;

public interface ElGamalReencryptFactorCTestData extends BasicValuesTestData {
	public static final String ELGAMAL_REENCRYPT_FACTOR_XML = "<r>"
			+ BIGINT_A_BASE64 + "</r>";
	public static final ElGamalReencryptFactorC ELGAMAL_REENCRYPT_FACTOR = new ElGamalReencryptFactorC(
			BIGINT_A);
	public static final ElGamalReencryptFactorC ELGAMAL_REENCRYPT_FACTOR_D = new ElGamalReencryptFactorC(
			BIGINT_D);
	public static final String ELGAMAL_REENCRYPT_FACTOR_NULL_XML = "<r></r>";

}
