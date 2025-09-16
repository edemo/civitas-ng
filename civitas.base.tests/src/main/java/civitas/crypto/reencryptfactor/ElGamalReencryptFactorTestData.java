package civitas.crypto.reencryptfactor;

import civitas.crypto.parameters.ElGamalParametersTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalReencryptFactorTestData
		extends ElGamalParametersTestData {

	public static final ElGamalReencryptFactor ENCRYPT_FACTOR_ZERO = new ElGamalReencryptFactor(
			ZERO);

	CivitasBigInteger FACTOR_EPRIME = BIGINT_A;
	CivitasBigInteger G_EXP_FACTOR = BIGINT_G.modPow(FACTOR_EPRIME, BIGINT_P);
	ElGamalReencryptFactor ELGAMAL_REENCRYPT_FACTOR_EPRIME = new ElGamalReencryptFactor(
			FACTOR_EPRIME);
	CivitasBigInteger FACTOR_E = BIGINT_D;
	ElGamalReencryptFactor ELGAMAL_REENCRYPT_FACTOR_E = new ElGamalReencryptFactor(
			FACTOR_E);
	ElGamalReencryptFactor ELGAMAL_REENCRYPT_FACTOR_RANDOMS_0 = new ElGamalReencryptFactor(
			RANDOMS_0);

	ElGamalReencryptFactor[] FACTORS = RANDOMS.subList(0, 3).stream()
			.map(x -> ELGAMAL_REENCRYPT_FACTOR_E).toList()
			.toArray(new ElGamalReencryptFactor[0]);
	CivitasBigInteger ZETA = FACTOR_EPRIME.modSubtract(FACTOR_E, BIGINT_Q);
}
