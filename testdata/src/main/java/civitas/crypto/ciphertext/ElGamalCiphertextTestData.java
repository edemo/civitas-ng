package civitas.crypto.ciphertext;

import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.stream.IntStream;

import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.msg.ElgamalMsgTestData;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.crypto.votecapabilityshare.VoteCapabilityShareTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalCiphertextTestData
		extends ElgamalMsgTestData, VoteCapabilityShareTestData {

	ElGamalCiphertext EL_GAMAL_CIPHERTEXT_ONE = new ElGamalCiphertext(ONE,
			ONE_ENCODED.m);
	ElGamalCiphertext EL_GAMAL_CIPHERTEXT_TWO = new ElGamalCiphertext(TWO,
			TWO_ENCODED.m);

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

	CivitasBigInteger CIPHERTEXT_EPRIME_A = BIGINT_G.modPow(FACTOR_EPRIME,
			BIGINT_P);
	CivitasBigInteger CIPHERTEXT_EPRIME_B = MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED
			.modMultiply(PUBKEY_E.modPow(FACTOR_EPRIME, BIGINT_P), BIGINT_P);
	public static ElGamalCiphertext CIPHERTEXT_EPRIME = new ElGamalCiphertext(
			CIPHERTEXT_EPRIME_A, CIPHERTEXT_EPRIME_B);

	public static final ElGamalCiphertext EL_GAMAL_CIPHERTEXT = new ElGamalCiphertext(
			GENERATOR_OTHER_POW_R1, BIGINT_D);

	ElGamalCiphertext ENCRYPTED_WITH_FACTOR_EPRIME = new ElGamalCiphertext(
			G_EXP_FACTOR, MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED.modMultiply(
					PUBKEY_EPRIME.modPow(FACTOR_EPRIME, BIGINT_P), BIGINT_P));

	public static final ElGamalCiphertext ENCRYPTED_ZERO_FACTOR = new ElGamalCiphertext(
			ONE, G_EXP_B);

	public static final int NO_OF_WELL_KNOWN_CIPHERTEXTS = 4;

	CiphertextList CIPHERTEXT_LIST = new CiphertextList(
			IntStream.range(0, NO_OF_WELL_KNOWN_CIPHERTEXTS)
					.mapToObj(i -> new ElGamalCiphertext(ONE,
							BIGINT_G.modPow(CivitasBigInteger.valueOf(i + 1), BIGINT_P)))
					.toList());

	byte[] VOTER_ADDITIONAL_ENV = "8+bob hash".getBytes();
	List<ElGamalSignedCiphertext> ENCRYPTED_SIGNED_VOTE_CAPABILITIES = VOTE_CAPABILITIES
			.stream()
			.map(x -> new ElGamalSignedCiphertext(
					mock(CivitasBigInteger.class,
							"ENCRYPTED_SIGNED_VOTE_CAPABILITIES_A"
									+ VOTE_CAPABILITIES.indexOf(x)),
					mock(CivitasBigInteger.class,
							"ENCRYPTED_SIGNED_VOTE_CAPABILITIES_B"
									+ VOTE_CAPABILITIES.indexOf(x)),
					mock(CivitasBigInteger.class,
							"ENCRYPTED_SIGNED_VOTE_CAPABILITIES_C"
									+ VOTE_CAPABILITIES.indexOf(x)),
					mock(CivitasBigInteger.class, "ENCRYPTED_SIGNED_VOTE_CAPABILITIES_D"
							+ VOTE_CAPABILITIES.indexOf(x))))
			.toList();
	List<ElGamalCiphertext> ENCRYPTED_VOTE_CAPABILITIES = ENCRYPTED_SIGNED_VOTE_CAPABILITIES
			.stream()
			.map(x -> mock(ElGamalCiphertext.class, "ENCRYPTED_VOTE_CAPABILITIES_"
					+ ENCRYPTED_SIGNED_VOTE_CAPABILITIES.indexOf(x)))
			.toList();
	ElGamalSignedCiphertext[] POSTED_CAPABILITIES = ENCRYPTED_SIGNED_VOTE_CAPABILITIES
			.toArray(new ElGamalSignedCiphertext[0]);
	ElGamalSignedCiphertext[] POSTED_CAPABILITIES_NONVERIFY = VOTE_CAPABILITIES
			.stream()
			.map(x -> mock(ElGamalSignedCiphertext.class,
					"POSTED_CAPABILITIES_NONVERIFY_" + VOTE_CAPABILITIES.indexOf(x)))
			.toList().toArray(new ElGamalSignedCiphertext[0]);

	List<ElGamalCiphertext> ENCRYPTED_VOTE_CAPABILITIES_WITH_EPRIME = VOTE_CAPABILITIES
			.stream()
			.map(x -> mock(ElGamalCiphertext.class,
					"ENCRYPTED_VOTE_CAPABILITIES_WITH_EPRIME_"
							+ VOTE_CAPABILITIES.indexOf(x)))
			.toList();

}
