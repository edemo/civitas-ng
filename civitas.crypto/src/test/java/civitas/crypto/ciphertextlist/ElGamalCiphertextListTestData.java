package civitas.crypto.ciphertextlist;

import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.stream.IntStream;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextTestData;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.crypto.votecapabilityshare.VoteCapabilityTestData;
import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;

public interface ElGamalCiphertextListTestData extends ElGamalCiphertextTestData, VoteCapabilityTestData {

	CiphertextList EMPTY_LIST = new CiphertextList();

	CiphertextList CIPHERTEXTLIST_ONE_LONG = new CiphertextList(List.of(EL_GAMAL_CIPHERTEXT_ONE));

	CiphertextList CIPHERTEXTLIST_TWO_LONG =
			new CiphertextList(List.of(EL_GAMAL_CIPHERTEXT_ONE, EL_GAMAL_CIPHERTEXT_TWO));

	CiphertextList CIPHERTEXTLIST_ZEROSIZED = new CiphertextList();
	CiphertextList CIPHERTEXTLIST_ONEINSIDE = new CiphertextList(List.of(CIPHERTEXT_E));
	CiphertextList CIPHERTEXTLIST = new CiphertextList(List.of(CIPHERTEXT_E, CIPHERTEXT_EPRIME));

	int NO_OF_WELL_KNOWN_CIPHERTEXTS = 4;

	CiphertextList CIPHERTEXT_LIST = new CiphertextList(IntStream.range(0, NO_OF_WELL_KNOWN_CIPHERTEXTS)
			.mapToObj(
					i -> new ElGamalCiphertext(ONE, BIGINT_G.modPow(CivitasBigIntegerFactory.obtain(i + 1), BIGINT_P)))
			.toList());
	List<ElGamalSignedCiphertext> ENCRYPTED_SIGNED_VOTE_CAPABILITIES = VOTE_CAPABILITIES.stream()
			.map(x -> new ElGamalSignedCiphertext(
					mock(
							CivitasBigInteger.class,
							"ENCRYPTED_SIGNED_VOTE_CAPABILITIES_A" + VOTE_CAPABILITIES.indexOf(x)),
					mock(
							CivitasBigInteger.class,
							"ENCRYPTED_SIGNED_VOTE_CAPABILITIES_B" + VOTE_CAPABILITIES.indexOf(x)),
					mock(
							CivitasBigInteger.class,
							"ENCRYPTED_SIGNED_VOTE_CAPABILITIES_C" + VOTE_CAPABILITIES.indexOf(x)),
					mock(
							CivitasBigInteger.class,
							"ENCRYPTED_SIGNED_VOTE_CAPABILITIES_D" + VOTE_CAPABILITIES.indexOf(x))))
			.toList();
	List<ElGamalCiphertext> ENCRYPTED_VOTE_CAPABILITIES = ENCRYPTED_SIGNED_VOTE_CAPABILITIES.stream()
			.map(x -> mock(
					ElGamalCiphertext.class,
					"ENCRYPTED_VOTE_CAPABILITIES_" + ENCRYPTED_SIGNED_VOTE_CAPABILITIES.indexOf(x)))
			.toList();
	List<ElGamalCiphertext> ENCRYPTED_VOTE_CAPABILITIES_WITH_EPRIME = VOTE_CAPABILITIES.stream()
			.map(x -> mock(
					ElGamalCiphertext.class, "ENCRYPTED_VOTE_CAPABILITIES_WITH_EPRIME_" + VOTE_CAPABILITIES.indexOf(x)))
			.toList();
	ElGamalSignedCiphertext[] POSTED_CAPABILITIES =
			ENCRYPTED_SIGNED_VOTE_CAPABILITIES.toArray(new ElGamalSignedCiphertext[0]);
	ElGamalSignedCiphertext[] POSTED_CAPABILITIES_NONVERIFY = VOTE_CAPABILITIES.stream()
			.map(x -> mock(
					ElGamalSignedCiphertext.class, "POSTED_CAPABILITIES_NONVERIFY_" + VOTE_CAPABILITIES.indexOf(x)))
			.toList()
			.toArray(new ElGamalSignedCiphertext[0]);

	List<CivitasBigInteger> DS = RANDOMS.subList(0, NO_OF_WELL_KNOWN_CIPHERTEXTS);
	List<CivitasBigInteger> RS = RANDOMS.subList(NO_OF_WELL_KNOWN_CIPHERTEXTS, NO_OF_WELL_KNOWN_CIPHERTEXTS * 2);

	CivitasBigInteger SUM = DS.stream().reduce(ZERO, (a, b) -> {
		if (b != DS.get(MY_CHOICE.ordinal())) {
			return a.modAdd(b, BIGINT_Q);
		}
		return a;
	});

	List<ElGamalCiphertextish> REENCRYPTED_VOTE_CAPABILITIES = ENCRYPTED_SIGNED_VOTE_CAPABILITIES.stream()
			.map(x -> (ElGamalCiphertextish) new ElGamalCiphertext(
					mock(
							CivitasBigInteger.class,
							"REENCRYPTED_VOTE_CAPABILITIES_A" + ENCRYPTED_SIGNED_VOTE_CAPABILITIES.indexOf(x)),
					mock(
							CivitasBigInteger.class,
							"REENCRYPTED_VOTE_CAPABILITIES_B" + ENCRYPTED_SIGNED_VOTE_CAPABILITIES.indexOf(x))))
			.toList();

	List<ElGamalCiphertextish> REENCRYPTED_VOTE_CAPABILITIES_WITH_KEY_E = ENCRYPTED_SIGNED_VOTE_CAPABILITIES.stream()
			.map(x -> (ElGamalCiphertextish) new ElGamalCiphertext(
					mock(
							CivitasBigInteger.class,
							"REENCRYPTED_VOTE_CAPABILITIES_E_A" + ENCRYPTED_SIGNED_VOTE_CAPABILITIES.indexOf(x)),
					mock(
							CivitasBigInteger.class,
							"REENCRYPTED_VOTE_CAPABILITIES_E_B" + ENCRYPTED_SIGNED_VOTE_CAPABILITIES.indexOf(x))))
			.toList();

	List<ElGamalCiphertext> PROOF_EPRIMES = List.of(
			new ElGamalCiphertext(
					mock(CivitasBigInteger.class, "eprime_a0"), mock(CivitasBigInteger.class, "eprime_b0")),
			new ElGamalCiphertext(
					mock(CivitasBigInteger.class, "eprime_a1"), mock(CivitasBigInteger.class, "eprime_b1")),
			new ElGamalCiphertext(
					mock(CivitasBigInteger.class, "eprime_a2"), mock(CivitasBigInteger.class, "eprime_b2")),
			new ElGamalCiphertext(
					mock(CivitasBigInteger.class, "eprime_a3"), mock(CivitasBigInteger.class, "eprime_b3")));
}
