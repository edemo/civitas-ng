package civitas.crypto.privatekey;

import civitas.crypto.parameters.ElGamalParametersTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalPrivateKeyTestData extends ElGamalParametersTestData {

	CivitasBigInteger PRIVKEY_E = BIGINT_A;
	CivitasBigInteger PRIVKEY_EPRIME = BIGINT_B;

	ElGamalPrivateKey EL_GAMAL_PRIVATE_KEY_E = new ElGamalPrivateKey(BIGINT_A, EL_GAMAL_PARAMETERS);
	ElGamalPrivateKey EL_GAMAL_PRIVATE_KEY_EPRIME = new ElGamalPrivateKey(BIGINT_B, EL_GAMAL_PARAMETERS);
	ElGamalPrivateKey EL_GAMAL_PRIVATE_KEY_C = new ElGamalPrivateKey(BIGINT_C, EL_GAMAL_PARAMETERS);

	String EL_GAMAL_PRIVATE_KEY_FILE = "el_gamal_private_key.xml";
	String EL_GAMAL_PRIVATE_KEY_E_XML = "<ElGamalPrivateKey><params>"
			+ EL_GAMAL_PARAMETERS_XML + "</params><x>" + BIGINT_A_BASE64
			+ "</x></ElGamalPrivateKey>";
}
