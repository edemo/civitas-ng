package civitas.crypto.ciphertext;

import java.util.Base64;
import java.util.List;
import java.util.stream.IntStream;

import civitas.DI;
import civitas.common.Util;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.msg.ElgamalMessageTestData;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.crypto.signedciphertext.SignAndEncrypt;
import civitas.crypto.votecapabilityshare.VoteCapabilityShareTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalCiphertextTestData
		extends ElgamalMessageTestData, VoteCapabilityShareTestData {

	CivitasBigInteger CIPHERTEXT_E_A = BIGINT_G.modPow(FACTOR_E, BIGINT_P);
	CivitasBigInteger CIPHERTEXT_E_B = MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED
			.modMultiply(PUBKEY_E.modPow(FACTOR_E, BIGINT_P), BIGINT_P);
	public static ElGamalCiphertext CIPHERTEXT_E = new ElGamalCiphertext(
			CIPHERTEXT_E_A, CIPHERTEXT_E_B);
	public static ElGamalCiphertext CIPHERTEXT_E_REENCRYPTED = new ElGamalCiphertext(
			CIPHERTEXT_E_A.modMultiply(BIGINT_G.modPow(RANDOMS_0, BIGINT_P),
					BIGINT_P),
			CIPHERTEXT_E_B.modMultiply(PUBKEY_EPRIME.modPow(RANDOMS_0, BIGINT_P),
					BIGINT_P));

	CivitasBigInteger CIPHERTEXT_ENCCAP_A = BIGINT_G.modPow(FACTOR_EPRIME,
			BIGINT_P);
	CivitasBigInteger CIPHERTEXT_ENCCAP_B = MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED
			.modMultiply(PUBKEY_E.modPow(FACTOR_EPRIME, BIGINT_P), BIGINT_P);
	public static ElGamalCiphertext CIPHERTEXT_ENCCAP = new ElGamalCiphertext(
			CIPHERTEXT_ENCCAP_A, CIPHERTEXT_ENCCAP_B);

	String CIPHERTEXT_ENCCAP_A_BASE64 = Base64.getEncoder()
			.encodeToString(CIPHERTEXT_ENCCAP_A.toByteArray());
	String CIPHERTEXT_ENCCAP_B_BASE64 = Base64.getEncoder()
			.encodeToString(CIPHERTEXT_ENCCAP_B.toByteArray());
	String CIPHERTEXT_ENCCAP_XML = "<ElGamalCiphertext><a>"
			+ CIPHERTEXT_ENCCAP_A_BASE64 + "</a><b>" + CIPHERTEXT_ENCCAP_B_BASE64
			+ "</b></ElGamalCiphertext>";

	CivitasBigInteger CIPHERTEXT_EPRIME_A = BIGINT_G.modPow(FACTOR_EPRIME,
			BIGINT_P);
	CivitasBigInteger CIPHERTEXT_EPRIME_B = MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED
			.modMultiply(PUBKEY_E.modPow(FACTOR_EPRIME, BIGINT_P), BIGINT_P);
	public static ElGamalCiphertext CIPHERTEXT_EPRIME = new ElGamalCiphertext(
			CIPHERTEXT_EPRIME_A, CIPHERTEXT_EPRIME_B);

	String CIPHERTEXT_E_B_BASE64 = Util.fromBigInt(CIPHERTEXT_E_B);
	String CIPHERTEXT_E_A_BASE64 = Util.fromBigInt(CIPHERTEXT_E_A);
	public static final String EL_GAMAL_CIPHERTEXT_E_XML = "<ElGamalCiphertext><a>"
			+ CIPHERTEXT_E_A_BASE64 + "</a><b>" + CIPHERTEXT_E_B_BASE64
			+ "</b></ElGamalCiphertext>";

	String CIPHERTEXT_EPRIME_B_BASE64 = Util.fromBigInt(CIPHERTEXT_EPRIME_B);
	String CIPHERTEXT_EPRIME_A_BASE64 = Util.fromBigInt(CIPHERTEXT_EPRIME_A);
	public static final String EL_GAMAL_CIPHERTEXT_EPRIME_XML = "<ElGamalCiphertext><a>"
			+ CIPHERTEXT_EPRIME_A_BASE64 + "</a><b>" + CIPHERTEXT_EPRIME_B_BASE64
			+ "</b></ElGamalCiphertext>";

	public static final String XML_ELGAMALCIPHERTEXT_TRUNCATED = "<ElGamalCiphertext><a>ESIQ9LFs";
	public static final String ELGAMALCIPHERTEXT_XML = "<ElGamalCiphertext><a>"
			+ SOMESTRING_BASE64 + "</a><b>Xje5W2KfxNk=</b></ElGamalCiphertext>";
	public static final String XML_ELGAMALCIPHERTEXT_ANULL = "<ElGamalCiphertext><a></a><b>Xje5W2KfxNk=</b></ElGamalCiphertext>";
	public static final String XML_ELGAMALCIPHERTEXT_BNULL = "<ElGamalCiphertext><a>Xje5W2KfxNk=</a><b></b></ElGamalCiphertext>";

	public static final String SHARED_KEY_CIPHERTEXT_XML = "<sharedKeyCiphertext>"
			+ SOMESTRING_BASE64 + "</sharedKeyCiphertext>";
	public static final String PUBLIC_KEY_CIPHERTEXT_XML = "<publicKeyCiphertext>"
			+ SOMESTRING_BASE64 + "</publicKeyCiphertext>";
	public static final String EL_GAMAL_CIPHERTEXT_NAIVE_XML = "<ElGamalCiphertext><a>42</a><b>24</b></ElGamalCiphertext>";
	public static final ElGamalCiphertext EL_GAMAL_CIPHERTEXT = new ElGamalCiphertext(
			GENERATOR_OTHER_POW_R1, BIGINT_D);
	public static final String EL_GAMAL_CIPHERTEXT_XML = "<ElGamalCiphertext><a>"
			+ GENERATOR_OTHER__POW_R1_BASE64 + "</a><b>" + BIGINT_D_BASE64
			+ "</b></ElGamalCiphertext>";

	ElGamalCiphertext ENCRYPTED_WITH_FACTOR_EPRIME = new ElGamalCiphertext(
			G_EXP_FACTOR, MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED.modMultiply(
					PUBKEY_EPRIME.modPow(FACTOR_EPRIME, BIGINT_P), BIGINT_P));

	public static final ElGamalCiphertext ENCRYPTED_ZERO_FACTOR = new ElGamalCiphertext(
			ONE, G_EXP_B);
	public static final String ENCRYPTED_ZERO_FACTOR_XML = "<ElGamalCiphertext><a>"
			+ ONE_BASE64 + "</a><b>" + G_EXP_B_BASE64 + "</b></ElGamalCiphertext>";

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

	public static final int NO_OF_WELL_KNOWN_CIPHERTEXTS = 4;

	CiphertextList CIPHERTEXT_LIST = new CiphertextList(
			IntStream.range(0, NO_OF_WELL_KNOWN_CIPHERTEXTS)
					.mapToObj(i -> new ElGamalCiphertext(ONE,
							BIGINT_G.modPow(CivitasBigInteger.valueOf(i + 1), BIGINT_P)))
					.toList());

	public static final String CIPHERTEXT_LIST_AS_XML = "<ciphertextList>"
			+ "<size>1</size>" + EL_GAMAL_CIPHERTEXT_E_XML + "</ciphertextList>";
	public static final String EMPTY_CIPHERTEXT_LIST_AS_XML = "<ciphertextList>"
			+ "<size>0</size></ciphertextList>";
	public static final String CIPHERTEXT_LIST_XML_WITH_NEGATIVE_LENGTH = "<ciphertextList>"
			+ "<size>-1</size></ciphertextList>";

	byte[] VOTER_ADDITIONAL_ENV = Base64.getDecoder()
			.decode("oW/1n32y+V2WGH4xPoMcyfIQbmQLhCcnhjV6UeaGPyI=");
	List<ElGamalSignedCiphertext> ENCRYPTED_SIGNED_VOTE_CAPABILITIES = VOTE_CAPABILITIES
			.stream()
			.map(x -> DI.get(SignAndEncrypt.class).apply(EL_GAMAL_PUBLIC_KEY_E, x,
					ELGAMAL_REENCRYPT_FACTOR_E, VOTER_ADDITIONAL_ENV))
			.toList();
	List<ElGamalCiphertext> ENCRYPTED_VOTE_CAPABILITIES = ENCRYPTED_SIGNED_VOTE_CAPABILITIES
			.stream().map(x -> new ElGamalCiphertext(x.a, x.b)).toList();
	ElGamalSignedCiphertext[] POSTED_CAPABILITIES = ENCRYPTED_SIGNED_VOTE_CAPABILITIES
			.toArray(new ElGamalSignedCiphertext[0]);
	ElGamalSignedCiphertext[] POSTED_CAPABILITIES_NONVERIFY = VOTE_CAPABILITIES
			.stream()
			.map(x -> DI.get(SignAndEncrypt.class).apply(EL_GAMAL_PUBLIC_KEY_E, x,
					ELGAMAL_REENCRYPT_FACTOR_E, BYTES))
			.toList().toArray(new ElGamalSignedCiphertext[0]);

	List<ElGamalCiphertext> ENCRYPTED_VOTE_CAPABILITIES_WITH_EPRIME = VOTE_CAPABILITIES
			.stream().map(x -> DI.get(ElGamalEncrypt.class)
					.apply(EL_GAMAL_PUBLIC_KEY_E, x, ELGAMAL_REENCRYPT_FACTOR_EPRIME))
			.toList();

}
