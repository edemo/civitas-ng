package civitas.crypto.concrete;

import civitas.util.CivitasBigInteger;

public interface ElGamalPrivateKeyCTestData extends ElGamalParametersCTestData {

	CivitasBigInteger PRIVKEY_E = BIGINT_A;
	CivitasBigInteger PRIVKEY_EPRIME = BIGINT_B;

	public static final ElGamalPrivateKeyC ELGAMAL_PRIVATE_KEY_E = new ElGamalPrivateKeyC(
			BIGINT_A, EL_GAMAL_PARAMETERS);
	public static final ElGamalPrivateKeyC ELGAMAL_PRIVATE_KEY_EPRIME = new ElGamalPrivateKeyC(
			BIGINT_B, EL_GAMAL_PARAMETERS);
	public static final ElGamalPrivateKeyC EL_GAMAL_PRIVATE_KEY_C = new ElGamalPrivateKeyC(
			BIGINT_C, EL_GAMAL_PARAMETERS);

	public static final String EL_GAMAL_PRIVATE_KEY_NULL_XML = "<elGamalPrivateKey><params></params><x></x></elGamalPrivateKey>";

}
