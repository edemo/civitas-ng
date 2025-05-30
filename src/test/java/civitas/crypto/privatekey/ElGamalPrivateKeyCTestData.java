package civitas.crypto.privatekey;

import civitas.crypto.parameters.ElGamalParametersCTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalPrivateKeyCTestData extends ElGamalParametersCTestData {

	CivitasBigInteger PRIVKEY_E = BIGINT_A;
	CivitasBigInteger PRIVKEY_EPRIME = BIGINT_B;

	public static final ElGamalPrivateKey ELGAMAL_PRIVATE_KEY_E = new ElGamalPrivateKey(
			BIGINT_A, EL_GAMAL_PARAMETERS);
	public static final ElGamalPrivateKey ELGAMAL_PRIVATE_KEY_EPRIME = new ElGamalPrivateKey(
			BIGINT_B, EL_GAMAL_PARAMETERS);
	public static final ElGamalPrivateKey EL_GAMAL_PRIVATE_KEY_C = new ElGamalPrivateKey(
			BIGINT_C, EL_GAMAL_PARAMETERS);

	public static final String EL_GAMAL_PRIVATE_KEY_NULL_XML = "<elGamalPrivateKey><params></params><x></x></elGamalPrivateKey>";

}
