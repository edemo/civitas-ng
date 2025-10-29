package civitas.crypto.publickey.tests;

import civitas.common.CommonUtil;
import civitas.crypto.privatekey.tests.ElGamalPrivateKeyTestData;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;

public interface ElGamalPublicKeyTestData extends ElGamalPrivateKeyTestData {

	CivitasBigInteger PUBKEY_EPRIME = BIGINT_G.modPow(PRIVKEY_EPRIME, BIGINT_P);
	String PUBKEY_EPRIME_BASE64 = CommonUtil.fromBigInt(PUBKEY_EPRIME);
	CivitasBigInteger PUBKEY_E = BIGINT_G.modPow(PRIVKEY_E, BIGINT_P);
	String PUBKEY_E_BASE64 = CommonUtil.fromBigInt(PUBKEY_E);
	ElGamalPublicKey EL_GAMAL_PUBLIC_KEY_EPRIME = new ElGamalPublicKey(PUBKEY_EPRIME, EL_GAMAL_PARAMETERS);
	ElGamalPublicKey EL_GAMAL_PUBLIC_KEY_E = new ElGamalPublicKey(PUBKEY_E, EL_GAMAL_PARAMETERS);
	ElGamalPublicKey EL_GAMAL_PUBLIC_KEY_E_BUT_OTHER_PARAMETERS =
			new ElGamalPublicKey(PUBKEY_E, EL_GAMAL_PARAMETERS_OTHER);
	ElGamalPublicKey EL_GAMAL_PUBLIC_KEY_A_USING_G_OTHER =
			new ElGamalPublicKey(BIGINT_G_OTHER.modPow(BIGINT_A, BIGINT_P), EL_GAMAL_PARAMETERS_OTHER);

	String EL_GAMAL_PUBLIC_KEY_FILE = "el_gamal_public_key.xml";

	String EL_GAMAL_PUBLIC_KEY_E_XML = "<elGamalPublicKey>" + "<params>"
			+ EL_GAMAL_PARAMETERS_XML + "</params><y>" + PUBKEY_E_BASE64
			+ "</y></elGamalPublicKey>";

	String EL_GAMAL_PUBLIC_KEY_NULL_XML = "<elGamalPublicKey>" + "<params></params><y></y></elGamalPublicKey>";
	String EL_GAMALPUBLIC_KEY_NAME = "ElGamalPublicKey-" + G_EXP_A_BASE64;
}
