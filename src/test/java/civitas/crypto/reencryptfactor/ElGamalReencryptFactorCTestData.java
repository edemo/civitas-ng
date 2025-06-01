package civitas.crypto.reencryptfactor;

import civitas.common.Util;
import civitas.crypto.parameters.ElGamalParametersTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalReencryptFactorCTestData
		extends ElGamalParametersTestData {
	public static final String ELGAMAL_REENCRYPT_FACTOR_XML = "<r>"
			+ BIGINT_A_BASE64 + "</r>";

	public static final ElGamalReencryptFactor ENCRYPT_FACTOR_ZERO = new ElGamalReencryptFactor(
			ZERO);

	public static final String ELGAMAL_REENCRYPT_FACTOR_NULL_XML = "<r></r>";
	public static final ElGamalReencryptFactor REENCRYPT_FACTOR_RANDOMS_1 = new ElGamalReencryptFactor(
			RANDOMS_1);// FIXME weed out
	CivitasBigInteger FACTOR_EPRIME = BIGINT_A;
	CivitasBigInteger G_EXP_FACTOR = BIGINT_G.modPow(FACTOR_EPRIME, BIGINT_P);
	public static final ElGamalReencryptFactor ELGAMAL_REENCRYPT_FACTOR_EPRIME = new ElGamalReencryptFactor(
			FACTOR_EPRIME);
	CivitasBigInteger FACTOR_E = BIGINT_D;
	String FACTOR_E_BASE64 = Util.fromBigInt(FACTOR_E);
	public static final ElGamalReencryptFactor ELGAMAL_REENCRYPT_FACTOR_E = new ElGamalReencryptFactor(
			FACTOR_E);

}
