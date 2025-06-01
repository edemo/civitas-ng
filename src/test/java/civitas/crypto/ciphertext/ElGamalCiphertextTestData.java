package civitas.crypto.ciphertext;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import civitas.common.Util;
import civitas.crypto.CryptoFactoryC;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.msg.ElgamalMessageTestData;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.util.CivitasBigInteger;

public interface ElGamalCiphertextTestData extends ElgamalMessageTestData {

	String ADDITIONALENV = "Árvíztűrő Tükörfúrógép";

	byte[] ADDITIONALENV_BYTES = ADDITIONALENV.getBytes();

	CivitasBigInteger CIPHERTEXT_E_A = BIGINT_G.modPow(FACTOR_E, BIGINT_P);
	CivitasBigInteger CIPHERTEXT_E_B = MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED
			.modMultiply(PUBKEY_E.modPow(FACTOR_E, BIGINT_P), BIGINT_P);
	public static ElGamalCiphertext CIPHERTEXT_E = new ElGamalCiphertext(
			CIPHERTEXT_E_A, CIPHERTEXT_E_B);

	CivitasBigInteger CIPHERTEXT_ENCCAP_A = BIGINT_G.modPow(FACTOR_EPRIME,
			BIGINT_P);
	CivitasBigInteger CIPHERTEXT_ENCCAP_B = MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED
			.modMultiply(PUBKEY_E.modPow(FACTOR_EPRIME, BIGINT_P), BIGINT_P);
	public static ElGamalCiphertext CIPHERTEXT_ENCCAP = new ElGamalCiphertext(
			CIPHERTEXT_ENCCAP_A, CIPHERTEXT_ENCCAP_B);

	CivitasBigInteger CIPHERTEXT_EPRIME_A = BIGINT_G.modPow(FACTOR_EPRIME,
			BIGINT_P);
	CivitasBigInteger CIPHERTEXT_EPRIME_B = MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED
			.modMultiply(PUBKEY_E.modPow(FACTOR_EPRIME, BIGINT_P), BIGINT_P);
	public static ElGamalCiphertext CIPHERTEXT_EPRIME = new ElGamalCiphertext(
			CIPHERTEXT_EPRIME_A, CIPHERTEXT_EPRIME_B);

	String CIPHERTEXT_E_B_BASE64 = Util.fromBigInt(CIPHERTEXT_E_B);
	String CIPHERTEXT_E_A_BASE64 = Util.fromBigInt(CIPHERTEXT_E_A);
	public static final String EL_GAMAL_CIPHERTEXT_E_XML = "<elGamalCiphertext><a>"
			+ CIPHERTEXT_E_A_BASE64 + "</a><b>" + CIPHERTEXT_E_B_BASE64
			+ "</b></elGamalCiphertext>";

	String CIPHERTEXT_EPRIME_B_BASE64 = Util.fromBigInt(CIPHERTEXT_EPRIME_B);
	String CIPHERTEXT_EPRIME_A_BASE64 = Util.fromBigInt(CIPHERTEXT_EPRIME_A);
	public static final String EL_GAMAL_CIPHERTEXT_EPRIME_XML = "<elGamalCiphertext><a>"
			+ CIPHERTEXT_EPRIME_A_BASE64 + "</a><b>" + CIPHERTEXT_EPRIME_B_BASE64
			+ "</b></elGamalCiphertext>";

	public static final String XML_ELGAMALCIPHERTEXT_TRUNCATED = "<elGamalCiphertext><a>ESIQ9LFs";
	public static final String ELGAMALCIPHERTEXT_XML = "<elGamalCiphertext><a>"
			+ SOMESTRING_BASE64 + "</a><b>Xje5W2KfxNk=</b></elGamalCiphertext>";
	public static final String XML_ELGAMALCIPHERTEXT_ANULL = "<elGamalCiphertext><a></a><b>Xje5W2KfxNk=</b></elGamalCiphertext>";
	public static final String XML_ELGAMALCIPHERTEXT_BNULL = "<elGamalCiphertext><a>Xje5W2KfxNk=</a><b></b></elGamalCiphertext>";

	public static final String SHARED_KEY_CIPHERTEXT_XML = "<sharedKeyCiphertext>"
			+ SOMESTRING_BASE64 + "</sharedKeyCiphertext>";
	public static final String PUBLIC_KEY_CIPHERTEXT_XML = "<publicKeyCiphertext>"
			+ SOMESTRING_BASE64 + "</publicKeyCiphertext>";
	public static final String EL_GAMAL_CIPHERTEXT_NAIVE_XML = "<elGamalCiphertext><a>42</a><b>24</b></elGamalCiphertext>";
	public static final ElGamalCiphertext EL_GAMAL_CIPHERTEXT = new ElGamalCiphertext(
			GENERATOR_OTHER_POW_R1, BIGINT_D);
	public static final String EL_GAMAL_CIPHERTEXT_XML = "<elGamalCiphertext><a>"
			+ GENERATOR_OTHER__POW_R1_BASE64 + "</a><b>" + BIGINT_D_BASE64
			+ "</b></elGamalCiphertext>";

	public static final ElGamalCiphertext ENCRYPTED_ZERO_FACTOR = new ElGamalCiphertext(
			ONE, G_EXP_B);
	public static final String ENCRYPTED_ZERO_FACTOR_XML = "<elGamalCiphertext><a>"
			+ ONE_BASE64 + "</a><b>" + G_EXP_B_BASE64 + "</b></elGamalCiphertext>";

	CivitasBigInteger HASH_OF_G_POW_RANDOMS0_G_EXP_FACTOR_MESSAGE_MUL_PUBKEY_POW_FACTOR_ADDITIONALENV = Util
			.asBigint("dHKDA9V8OEUx/Z2A7TLdgbG7OdIZTbg860iANjgFJMI=");
	CivitasBigInteger RANDOMS_0_PLUS_HASH_MUL_FACTOR = RANDOMS_0.modAdd(
			HASH_OF_G_POW_RANDOMS0_G_EXP_FACTOR_MESSAGE_MUL_PUBKEY_POW_FACTOR_ADDITIONALENV
					.modMultiply(FACTOR_E, BIGINT_Q),
			BIGINT_Q);

	ElGamalSignedCiphertext SIGNED_CIPHERTEXT_OF_MESSAGE_WITH_FACTOR_RANDOM0_ADDITIONALENV = new ElGamalSignedCiphertext(
			CIPHERTEXT_E_A, CIPHERTEXT_E_B,
			HASH_OF_G_POW_RANDOMS0_G_EXP_FACTOR_MESSAGE_MUL_PUBKEY_POW_FACTOR_ADDITIONALENV,
			RANDOMS_0_PLUS_HASH_MUL_FACTOR);

	public static final CryptoFactoryC factory = CryptoFactoryC.singleton();

	public static final int NO_OF_WELL_KNOWN_CIPHERTEXTS = 4;

	CiphertextList CIPHERTEXT_LIST = new CiphertextList(
			IntStream.range(0, NO_OF_WELL_KNOWN_CIPHERTEXTS)
					.mapToObj(i -> new ElGamalCiphertext(ONE,
							BIGINT_G.modPow(CivitasBigInteger.valueOf(i + 1), BIGINT_P)))
					.collect(Collectors.toList()));

	public static final String CIPHERTEXT_LIST_AS_XML = "<ciphertextList>"
			+ "<size>1</size>" + EL_GAMAL_CIPHERTEXT_E_XML + "</ciphertextList>";
	public static final String EMPTY_CIPHERTEXT_LIST_AS_XML = "<ciphertextList>"
			+ "<size>0</size></ciphertextList>";
	public static final String CIPHERTEXT_LIST_XML_WITH_NEGATIVE_LENGTH = "<ciphertextList>"
			+ "<size>-1</size></ciphertextList>";

}
