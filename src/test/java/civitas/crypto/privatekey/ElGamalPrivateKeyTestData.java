package civitas.crypto.privatekey;

import java.util.Map;

import civitas.crypto.parameters.ElGamalParametersTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalPrivateKeyTestData extends ElGamalParametersTestData {

	CivitasBigInteger PRIVKEY_E = BIGINT_A;
	CivitasBigInteger PRIVKEY_EPRIME = BIGINT_B;

	public static final ElGamalPrivateKey EL_GAMAL_PRIVATE_KEY_E = new ElGamalPrivateKey(
			BIGINT_A, EL_GAMAL_PARAMETERS);
	public static final ElGamalPrivateKey EL_GAMAL_PRIVATE_KEY_EPRIME = new ElGamalPrivateKey(
			BIGINT_B, EL_GAMAL_PARAMETERS);
	public static final ElGamalPrivateKey EL_GAMAL_PRIVATE_KEY_C = new ElGamalPrivateKey(
			BIGINT_C, EL_GAMAL_PARAMETERS);

	public static final String EL_GAMAL_PRIVATE_KEY_NULL_XML = "<elGamalPrivateKey><params></params><x></x></elGamalPrivateKey>";
	public static final String EL_GAMAL_PRIVATE_KEY_E_XML = "<elGamalPrivateKey><params>"
			+ EL_GAMAL_PARAMETERS_XML + "</params><x>" + BIGINT_A_BASE64
			+ "</x></elGamalPrivateKey>";
	public static final String EL_GAMAL_PRIVATE_KEY_EPRIME_XML = "<elGamalPrivateKey><params>"
			+ EL_GAMAL_PARAMETERS_XML + "</params><x>" + BIGINT_B_BASE64
			+ "</x></elGamalPrivateKey>";
	public static final Map<String, ElGamalPrivateKey> EL_GAMAL_PRIVATE_KEY_MOCKING = Map
			.of(EL_GAMAL_PRIVATE_KEY_E_XML, EL_GAMAL_PRIVATE_KEY_E,
					EL_GAMAL_PRIVATE_KEY_EPRIME_XML, EL_GAMAL_PRIVATE_KEY_EPRIME);

}
