package civitas.crypto.publickey;

import java.util.Map;

import civitas.common.Util;
import civitas.crypto.privatekey.ElGamalPrivateKeyTestData;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalPublicKeyTestData
		extends ElGamalPrivateKeyTestData, ElGamalReencryptFactorTestData {

	CivitasBigInteger PUBKEY_EPRIME = BIGINT_G.modPow(PRIVKEY_EPRIME, BIGINT_P);
	String PUBKEY_EPRIME_BASE64 = Util.fromBigInt(PUBKEY_EPRIME);
	CivitasBigInteger PUBKEY_E = BIGINT_G.modPow(PRIVKEY_E, BIGINT_P);
	String PUBKEY_E_BASE64 = Util.fromBigInt(PUBKEY_E);
	public static final ElGamalPublicKey EL_GAMAL_PUBLIC_KEY_EPRIME = new ElGamalPublicKey(
			PUBKEY_EPRIME, EL_GAMAL_PARAMETERS);
	public static final ElGamalPublicKey EL_GAMAL_PUBLIC_KEY_E = new ElGamalPublicKey(
			PUBKEY_E, EL_GAMAL_PARAMETERS);
	public static final ElGamalPublicKey EL_GAMAL_PUBLIC_KEY_E_BUT_OTHER_PARAMETERS = new ElGamalPublicKey(
			PUBKEY_E, EL_GAMAL_PARAMETERS_OTHER);
	public static final ElGamalPublicKey EL_GAMAL_PUBLIC_KEY_A_USING_G_OTHER = new ElGamalPublicKey(
			BIGINT_G_OTHER.modPow(BIGINT_A, BIGINT_P), EL_GAMAL_PARAMETERS_OTHER);

	String EL_GAMAL_PUBLIC_KEY_EPRIME_XML = "<elGamalPublicKey>" + "<params>"
			+ EL_GAMAL_PARAMETERS_XML + "</params><y>" + PUBKEY_EPRIME_BASE64
			+ "</y></elGamalPublicKey>";
	String EL_GAMAL_PUBLIC_KEY_E_XML = "<elGamalPublicKey>" + "<params>"
			+ EL_GAMAL_PARAMETERS_XML + "</params><y>" + PUBKEY_E_BASE64
			+ "</y></elGamalPublicKey>";
	public static final String EL_GAMAL_PUBLIC_KEY_XML = "<elGamalPublicKey><params>"
			+ EL_GAMAL_PARAMETERS_XML + "</params><y>" + G_EXP_A_BASE64
			+ "</y></elGamalPublicKey>";

	public static final String EL_GAMAL_PUBLIC_KEY_NULL_XML = "<elGamalPublicKey>"
			+ "<params></params><y></y></elGamalPublicKey>";
	public static final String EL_GAMALPUBLIC_KEY_NAME = "ElGamalPublicKey-"
			+ G_EXP_A_BASE64;
	CivitasBigInteger ZETA = FACTOR_EPRIME.modSubtract(FACTOR_E, BIGINT_Q);// E:key2,factor2,
																																					// eprime:
																																					// key,factor
	Map<String, ElGamalPublicKey> EL_GAMAL_PUBLIC_KEY_MOCKING = Map.of(
			EL_GAMAL_PUBLIC_KEY_E_XML, EL_GAMAL_PUBLIC_KEY_E,
			EL_GAMAL_PUBLIC_KEY_EPRIME_XML, EL_GAMAL_PUBLIC_KEY_EPRIME);

}
