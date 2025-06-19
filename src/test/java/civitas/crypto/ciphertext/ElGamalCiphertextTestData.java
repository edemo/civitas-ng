package civitas.crypto.ciphertext;

import java.util.List;
import java.util.stream.IntStream;

import civitas.DI;
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
