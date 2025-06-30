package civitas.crypto.reencryptfactor;

import civitas.crypto.parameters.ElGamalParametersTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalReencryptFactorTestData
		extends ElGamalParametersTestData {

	public static final ElGamalReencryptFactor ENCRYPT_FACTOR_ZERO = new ElGamalReencryptFactor(
			ZERO);

	CivitasBigInteger FACTOR_EPRIME = BIGINT_A;
	CivitasBigInteger G_EXP_FACTOR = BIGINT_G.modPow(FACTOR_EPRIME, BIGINT_P);
	public static final ElGamalReencryptFactor ELGAMAL_REENCRYPT_FACTOR_EPRIME = new ElGamalReencryptFactor(
			FACTOR_EPRIME);
	CivitasBigInteger FACTOR_E = BIGINT_D;
	public static final ElGamalReencryptFactor ELGAMAL_REENCRYPT_FACTOR_E = new ElGamalReencryptFactor(
			FACTOR_E);

	ElGamalReencryptFactor[] FACTORS = RANDOMS.subList(0, 3).stream()
			.map(x -> ELGAMAL_REENCRYPT_FACTOR_E).toList()
			.toArray(new ElGamalReencryptFactor[0]);

}
