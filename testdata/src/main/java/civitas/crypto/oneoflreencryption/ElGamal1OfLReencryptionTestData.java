package civitas.crypto.oneoflreencryption;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import civitas.common.ConstructTestData;
import civitas.common.Util;
import civitas.common.VoteChoice;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextTestData;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.proof1ofl.ElGamalProof1OfL;
import civitas.crypto.proofdvr.ElGamalProofDVR;
import civitas.util.CivitasBigInteger;

public interface ElGamal1OfLReencryptionTestData
		extends ElGamalCiphertextTestData {

	public static final VoteChoice MY_CHOICE = VoteChoice.I_BEATS_J;

	List<CivitasBigInteger> DS = RANDOMS.subList(0, NO_OF_WELL_KNOWN_CIPHERTEXTS);
	List<CivitasBigInteger> RS = RANDOMS.subList(NO_OF_WELL_KNOWN_CIPHERTEXTS,
			NO_OF_WELL_KNOWN_CIPHERTEXTS * 2);

	CivitasBigInteger SUM = DS.stream().reduce(ZERO, (a, b) -> {
		if (b != DS.get(MY_CHOICE.ordinal()))
			return a.modAdd(b, BIGINT_Q);
		return a;
	});

	Map<VoteChoice, ElGamalCiphertext> REENCRYPTED_CHOICE_MAP = ConstructTestData
			.constructTestData(CHOICES,
					(choice) -> new ElGamalCiphertext(
							CIPHERTEXT_LIST.get(choice.ordinal()).getA()
									.modMultiply(BIGINT_G.modPow(FACTOR_E, BIGINT_P), BIGINT_P),
							CIPHERTEXT_LIST.get(choice.ordinal()).getB()
									.modMultiply(PUBKEY_E.modPow(FACTOR_E, BIGINT_P), BIGINT_P)));

	CivitasBigInteger REENCRYPTED_WELL_KNOWN_CHOICE_A = CIPHERTEXT_LIST
			.get(MY_CHOICE.ordinal()).getA()
			.modMultiply(BIGINT_G.modPow(FACTOR_E, BIGINT_P), BIGINT_P);
	CivitasBigInteger REENCRYPTED_WELL_KNOWN_CHOICE_B = CIPHERTEXT_LIST
			.get(MY_CHOICE.ordinal()).getB()
			.modMultiply(PUBKEY_E.modPow(FACTOR_E, BIGINT_P), BIGINT_P);

	ElGamalCiphertext REENCRYPTED_WELL_KNOWN_CHOICE = new ElGamalCiphertext(
			REENCRYPTED_WELL_KNOWN_CHOICE_A, REENCRYPTED_WELL_KNOWN_CHOICE_B);

	CivitasBigInteger w = (FACTOR_E.modNegate(BIGINT_Q)
			.modMultiply(DS.get(MY_CHOICE.ordinal()), BIGINT_Q))
			.modAdd(RS.get(MY_CHOICE.ordinal()), BIGINT_Q);

	List<CivitasBigInteger> EL_GAMAL_PROOF_1_OF_L_AS = IntStream
			.range(0, NO_OF_WELL_KNOWN_CIPHERTEXTS).mapToObj(i -> {
				return CIPHERTEXT_LIST.get(i).getA()
						.modDivide(REENCRYPTED_WELL_KNOWN_CHOICE_A, BIGINT_P)
						.modPow(DS.get(i), BIGINT_P)
						.modMultiply(BIGINT_G.modPow(RS.get(i), BIGINT_P), BIGINT_P)
						.mod(BIGINT_P);
			}).toList();
	List<CivitasBigInteger> EL_GAMAL_PROOF_1_OF_L_BS = IntStream
			.range(0, NO_OF_WELL_KNOWN_CIPHERTEXTS).mapToObj(i -> {
				return CIPHERTEXT_LIST.get(i).getB()
						.modDivide(REENCRYPTED_WELL_KNOWN_CHOICE_B, BIGINT_P)
						.modPow(DS.get(i), BIGINT_P)
						.modMultiply(PUBKEY_E.modPow(RS.get(i), BIGINT_P), BIGINT_P)
						.mod(BIGINT_P);
			}).toList();

	List<CivitasBigInteger> EL_GAMAL_PROOF_1_OF_L_ENV = ((Supplier<List<CivitasBigInteger>>) () -> {
		ArrayList<CivitasBigInteger> d = new ArrayList<>(
				2 + 4 * NO_OF_WELL_KNOWN_CIPHERTEXTS);
		d.add(REENCRYPTED_WELL_KNOWN_CHOICE_A);
		d.add(REENCRYPTED_WELL_KNOWN_CHOICE_B);
		for (int i = 0; i < NO_OF_WELL_KNOWN_CIPHERTEXTS; i++) {
			d.add(CIPHERTEXT_LIST.get(i).getA());
			d.add(CIPHERTEXT_LIST.get(i).getB());
			d.add(EL_GAMAL_PROOF_1_OF_L_AS.get(i));
			d.add(EL_GAMAL_PROOF_1_OF_L_BS.get(i));
		}
		return d;
	}).get();

	String EL_GAMAL_PROOF_1_OF_L_HASH_BASE64 = "TqT++ODFynAUZo5awzSUsEXBXCS6HlC2CkKk6WEG1e4=";
	CivitasBigInteger EL_GAMAL_PROOF_1_OF_L_HASH = Util
			.asBigint(EL_GAMAL_PROOF_1_OF_L_HASH_BASE64);

	CivitasBigInteger EL_GAMAL_PROOF_1_OF_L_DV = EL_GAMAL_PROOF_1_OF_L_HASH
			.modSubtract(SUM, BIGINT_Q);
	CivitasBigInteger EL_GAMAL_PROOF_1_OF_L_RV = w.modAdd(
			FACTOR_E.modMultiply(EL_GAMAL_PROOF_1_OF_L_DV, BIGINT_Q), BIGINT_Q);

	public static final List<CivitasBigInteger> DVS = ((Supplier<List<CivitasBigInteger>>) () -> {
		List<CivitasBigInteger> d = new ArrayList<>(DS);
		d.set(MY_CHOICE.ordinal(), EL_GAMAL_PROOF_1_OF_L_DV);
		return d;
	}).get();

	public static final List<CivitasBigInteger> RVS = ((Supplier<List<CivitasBigInteger>>) () -> {
		List<CivitasBigInteger> r = new ArrayList<>(RS);
		r.set(MY_CHOICE.ordinal(), EL_GAMAL_PROOF_1_OF_L_RV);
		return r;
	}).get();

	public static final List<CivitasBigInteger> DVS_BAD = ((Supplier<List<CivitasBigInteger>>) () -> {
		List<CivitasBigInteger> d = new ArrayList<>(DVS);
		d.set(MY_CHOICE.ordinal(), BIGINT_D);
		return d;
	}).get();

	public static final ElGamalProof1OfL EL_GAMAL_PROOF_1_OF_L = new ElGamalProof1OfL(
			NO_OF_WELL_KNOWN_CIPHERTEXTS,
			DVS.toArray(new CivitasBigInteger[NO_OF_WELL_KNOWN_CIPHERTEXTS]),
			RVS.toArray(new CivitasBigInteger[NO_OF_WELL_KNOWN_CIPHERTEXTS]));

	public static final ElGamalProof1OfL EL_GAMAL_PROOF_1_OF_L_BAD = new ElGamalProof1OfL(
			NO_OF_WELL_KNOWN_CIPHERTEXTS,
			DVS_BAD.toArray(new CivitasBigInteger[NO_OF_WELL_KNOWN_CIPHERTEXTS]),
			RVS.toArray(new CivitasBigInteger[NO_OF_WELL_KNOWN_CIPHERTEXTS]));

	public static final ElGamal1OfLReencryption EL_GAMAL_1_OF_L_REENCRYPTION = new ElGamal1OfLReencryption(
			REENCRYPTED_WELL_KNOWN_CHOICE, EL_GAMAL_PROOF_1_OF_L);

	Map<VoteChoice, ElGamalProof1OfL> EL_GAMAL_PROOF_1_OF_L_MAP = ConstructTestData
			.constructTestData(CHOICES,
					(i) -> new ElGamalProof1OfL(NO_OF_WELL_KNOWN_CIPHERTEXTS,
							DVS.stream().map(x -> {
								if (DVS.indexOf(x) == i.ordinal())
									return mock(CivitasBigInteger.class, "dvs" + i);
								else
									return x;
							}).toList().toArray(new CivitasBigInteger[0]),
							RVS.stream().map(x -> {
								if (RVS.indexOf(x) == i.ordinal())
									return mock(CivitasBigInteger.class, "rvs" + i);
								else
									return x;
							}).toList().toArray(new CivitasBigInteger[0])));
	Map<VoteChoice, ElGamal1OfLReencryption> EL_GAMAL_1_OF_L_REENCRYPTION_MAP = ConstructTestData
			.constructTestData(CHOICES,
					(choice) -> new ElGamal1OfLReencryption(
							REENCRYPTED_CHOICE_MAP.get(choice),
							EL_GAMAL_PROOF_1_OF_L_MAP.get(choice)));

	List<ElGamalCiphertextish> REENCRYPTED_VOTE_CAPABILITIES = ENCRYPTED_SIGNED_VOTE_CAPABILITIES
			.stream()
			.map(
					x -> (ElGamalCiphertextish) new ElGamalCiphertext(
							mock(CivitasBigInteger.class,
									"REENCRYPTED_VOTE_CAPABILITIES_A"
											+ ENCRYPTED_SIGNED_VOTE_CAPABILITIES.indexOf(x)),
							mock(CivitasBigInteger.class,
									"REENCRYPTED_VOTE_CAPABILITIES_B"
											+ ENCRYPTED_SIGNED_VOTE_CAPABILITIES.indexOf(x))))
			.toList();

	List<ElGamalCiphertextish> REENCRYPTED_VOTE_CAPABILITIES_WITH_KEY_E = ENCRYPTED_SIGNED_VOTE_CAPABILITIES
			.stream()
			.map(
					x -> (ElGamalCiphertextish) new ElGamalCiphertext(
							mock(CivitasBigInteger.class,
									"REENCRYPTED_VOTE_CAPABILITIES_E_A"
											+ ENCRYPTED_SIGNED_VOTE_CAPABILITIES.indexOf(x)),
							mock(CivitasBigInteger.class,
									"REENCRYPTED_VOTE_CAPABILITIES_E_B"
											+ ENCRYPTED_SIGNED_VOTE_CAPABILITIES.indexOf(x))))
			.toList();

	List<ElGamalCiphertext> PROOF_EPRIMES = List.of(
			new ElGamalCiphertext(mock(CivitasBigInteger.class, "eprime_a0"),
					mock(CivitasBigInteger.class, "eprime_b0")),
			new ElGamalCiphertext(mock(CivitasBigInteger.class, "eprime_a1"),
					mock(CivitasBigInteger.class, "eprime_b1")),
			new ElGamalCiphertext(mock(CivitasBigInteger.class, "eprime_a2"),
					mock(CivitasBigInteger.class, "eprime_b2")),
			new ElGamalCiphertext(mock(CivitasBigInteger.class, "eprime_a3"),
					mock(CivitasBigInteger.class, "eprime_b3")));
	List<ElGamalProofDVR> PROOF_LIST = ENCRYPTED_SIGNED_VOTE_CAPABILITIES.stream()
			.map(x -> new ElGamalProofDVR(x,
					PROOF_EPRIMES.get(ENCRYPTED_SIGNED_VOTE_CAPABILITIES.indexOf(x)),
					mock(CivitasBigInteger.class), mock(CivitasBigInteger.class),
					mock(CivitasBigInteger.class), mock(CivitasBigInteger.class)))
			.toList();
	ElGamalProofDVR[] PROOFS = PROOF_LIST.toArray(new ElGamalProofDVR[0]);

	ElGamalProofDVR[] PROOFS_CAP_NONVERIFY = PROOF_LIST.stream()
			.map(x -> new ElGamalProofDVR(
					POSTED_CAPABILITIES_NONVERIFY[PROOF_LIST.indexOf(x)], x.eprime, x.c,
					x.w, x.r, x.u))
			.toList().toArray(new ElGamalProofDVR[0]);
}
