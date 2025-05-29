package civitas.crypto.concrete;

import civitas.common.Util;
import civitas.util.CivitasBigInteger;

public interface ElGamalReencryptFactorCTestData
		extends ElGamalParametersCTestData {
	public static final String ELGAMAL_REENCRYPT_FACTOR_XML = "<r>"
			+ BIGINT_A_BASE64 + "</r>";

	public static final ElGamalReencryptFactorC ENCRYPT_FACTOR_ZERO = new ElGamalReencryptFactorC(
			ZERO);

	public static final String ELGAMAL_REENCRYPT_FACTOR_NULL_XML = "<r></r>";
	public static final ElGamalReencryptFactorC REENCRYPT_FACTOR_RANDOMS_1 = new ElGamalReencryptFactorC(
			RANDOMS_1);// FIXME weed out
	CivitasBigInteger FACTOR_EPRIME = BIGINT_A;
	CivitasBigInteger G_EXP_FACTOR = BIGINT_G.modPow(FACTOR_EPRIME, BIGINT_P);
	public static final ElGamalReencryptFactorC ELGAMAL_REENCRYPT_FACTOR_EPRIME = new ElGamalReencryptFactorC(
			FACTOR_EPRIME);
	CivitasBigInteger FACTOR_E = BIGINT_D;
	String FACTOR_E_BASE64 = Util.fromBigInt(FACTOR_E);
	public static final ElGamalReencryptFactorC ELGAMAL_REENCRYPT_FACTOR_E = new ElGamalReencryptFactorC(
			FACTOR_E);

}
