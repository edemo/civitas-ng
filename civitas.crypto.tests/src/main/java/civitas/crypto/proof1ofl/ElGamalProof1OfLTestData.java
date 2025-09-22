package civitas.crypto.proof1ofl;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import civitas.common.CommonUtil;
import civitas.common.ConstructTestData;
import civitas.common.VoteChoice;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertextlist.ElGamalCiphertextListTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalProof1OfLTestData extends ElGamalCiphertextListTestData {

	CivitasBigInteger REENCRYPTED_WELL_KNOWN_CHOICE_A =
			CIPHERTEXT_LIST.get(MY_CHOICE.ordinal()).getA().modMultiply(BIGINT_G.modPow(FACTOR_E, BIGINT_P), BIGINT_P);
	CivitasBigInteger REENCRYPTED_WELL_KNOWN_CHOICE_B =
			CIPHERTEXT_LIST.get(MY_CHOICE.ordinal()).getB().modMultiply(PUBKEY_E.modPow(FACTOR_E, BIGINT_P), BIGINT_P);

	ElGamalCiphertext REENCRYPTED_WELL_KNOWN_CHOICE =
			new ElGamalCiphertext(REENCRYPTED_WELL_KNOWN_CHOICE_A, REENCRYPTED_WELL_KNOWN_CHOICE_B);

	CivitasBigInteger w = FACTOR_E.modNegate(BIGINT_Q)
			.modMultiply(DS.get(MY_CHOICE.ordinal()), BIGINT_Q)
			.modAdd(RS.get(MY_CHOICE.ordinal()), BIGINT_Q);

	List<CivitasBigInteger> EL_GAMAL_PROOF_1_OF_L_AS = IntStream.range(0, NO_OF_WELL_KNOWN_CIPHERTEXTS)
			.mapToObj(i -> CIPHERTEXT_LIST
					.get(i)
					.getA()
					.modDivide(REENCRYPTED_WELL_KNOWN_CHOICE_A, BIGINT_P)
					.modPow(DS.get(i), BIGINT_P)
					.modMultiply(BIGINT_G.modPow(RS.get(i), BIGINT_P), BIGINT_P)
					.mod(BIGINT_P))
			.toList();
	List<CivitasBigInteger> EL_GAMAL_PROOF_1_OF_L_BS = IntStream.range(0, NO_OF_WELL_KNOWN_CIPHERTEXTS)
			.mapToObj(i -> CIPHERTEXT_LIST
					.get(i)
					.getB()
					.modDivide(REENCRYPTED_WELL_KNOWN_CHOICE_B, BIGINT_P)
					.modPow(DS.get(i), BIGINT_P)
					.modMultiply(PUBKEY_E.modPow(RS.get(i), BIGINT_P), BIGINT_P)
					.mod(BIGINT_P))
			.toList();

	List<CivitasBigInteger> EL_GAMAL_PROOF_1_OF_L_ENV = ((Supplier<List<CivitasBigInteger>>) () -> {
				ArrayList<CivitasBigInteger> d = new ArrayList<>(2 + 4 * NO_OF_WELL_KNOWN_CIPHERTEXTS);
				d.add(REENCRYPTED_WELL_KNOWN_CHOICE_A);
				d.add(REENCRYPTED_WELL_KNOWN_CHOICE_B);
				for (int i = 0; i < NO_OF_WELL_KNOWN_CIPHERTEXTS; i++) {
					d.add(CIPHERTEXT_LIST.get(i).getA());
					d.add(CIPHERTEXT_LIST.get(i).getB());
					d.add(EL_GAMAL_PROOF_1_OF_L_AS.get(i));
					d.add(EL_GAMAL_PROOF_1_OF_L_BS.get(i));
				}
				return d;
			})
			.get();

	String EL_GAMAL_PROOF_1_OF_L_HASH_BASE64 = "TqT++ODFynAUZo5awzSUsEXBXCS6HlC2CkKk6WEG1e4=";
	CivitasBigInteger EL_GAMAL_PROOF_1_OF_L_HASH = CommonUtil.asBigint(EL_GAMAL_PROOF_1_OF_L_HASH_BASE64);

	CivitasBigInteger EL_GAMAL_PROOF_1_OF_L_DV = EL_GAMAL_PROOF_1_OF_L_HASH.modSubtract(SUM, BIGINT_Q);
	CivitasBigInteger EL_GAMAL_PROOF_1_OF_L_RV =
			w.modAdd(FACTOR_E.modMultiply(EL_GAMAL_PROOF_1_OF_L_DV, BIGINT_Q), BIGINT_Q);

	List<CivitasBigInteger> DVS = ((Supplier<List<CivitasBigInteger>>) () -> {
				List<CivitasBigInteger> d = new ArrayList<>(DS);
				d.set(MY_CHOICE.ordinal(), EL_GAMAL_PROOF_1_OF_L_DV);
				return d;
			})
			.get();

	List<CivitasBigInteger> RVS = ((Supplier<List<CivitasBigInteger>>) () -> {
				List<CivitasBigInteger> r = new ArrayList<>(RS);
				r.set(MY_CHOICE.ordinal(), EL_GAMAL_PROOF_1_OF_L_RV);
				return r;
			})
			.get();

	List<CivitasBigInteger> DVS_BAD = ((Supplier<List<CivitasBigInteger>>) () -> {
				List<CivitasBigInteger> d = new ArrayList<>(DVS);
				d.set(MY_CHOICE.ordinal(), BIGINT_D);
				return d;
			})
			.get();

	ElGamalProof1OfL EL_GAMAL_PROOF_1_OF_L = new ElGamalProof1OfL(
			NO_OF_WELL_KNOWN_CIPHERTEXTS, DVS.toArray(new CivitasBigInteger[0]), RVS.toArray(new CivitasBigInteger[0]));

	ElGamalProof1OfL EL_GAMAL_PROOF_1_OF_L_BAD = new ElGamalProof1OfL(
			NO_OF_WELL_KNOWN_CIPHERTEXTS,
			DVS_BAD.toArray(new CivitasBigInteger[0]),
			RVS.toArray(new CivitasBigInteger[0]));

	Map<VoteChoice, ElGamalProof1OfL> EL_GAMAL_PROOF_1_OF_L_MAP = ConstructTestData.constructTestData(
			CHOICES,
			i -> new ElGamalProof1OfL(
					NO_OF_WELL_KNOWN_CIPHERTEXTS,
					DVS.stream()
							.map(x -> {
								if (DVS.indexOf(x) == i.ordinal()) {
									return mock(CivitasBigInteger.class, "dvs" + i);
								} else {
									return x;
								}
							})
							.toList()
							.toArray(new CivitasBigInteger[0]),
					RVS.stream()
							.map(x -> {
								if (RVS.indexOf(x) == i.ordinal()) {
									return mock(CivitasBigInteger.class, "rvs" + i);
								} else {
									return x;
								}
							})
							.toList()
							.toArray(new CivitasBigInteger[0])));
}
