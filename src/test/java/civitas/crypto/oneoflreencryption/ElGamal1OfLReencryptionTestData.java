package civitas.crypto.oneoflreencryption;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import civitas.common.CommonConstants;
import civitas.common.ConstructTestData;
import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextTestData;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.proof1ofl.ConstructElGamalProof1OfL;
import civitas.crypto.proof1ofl.ElGamalProof1OfL;
import civitas.util.CivitasBigInteger;
import civitas.util.DI;

public interface ElGamal1OfLReencryptionTestData
		extends ElGamalCiphertextTestData {

	public static final int MY_CHOICE = CommonConstants.VOTE_CHOICE_I_BEATS_J;

	List<CivitasBigInteger> DS = RANDOMS.subList(0, NO_OF_WELL_KNOWN_CIPHERTEXTS);
	List<CivitasBigInteger> RS = RANDOMS.subList(NO_OF_WELL_KNOWN_CIPHERTEXTS,
			NO_OF_WELL_KNOWN_CIPHERTEXTS * 2);

	CivitasBigInteger SUM = DS.stream().reduce(ZERO, (a, b) -> {
		if (b != DS.get(MY_CHOICE))
			return a.modAdd(b, BIGINT_Q);
		return a;
	});

	List<Integer> VOTE_CHOICES = List.of(CommonConstants.VOTE_CHOICE_I_BEATS_J,
			CommonConstants.VOTE_CHOICE_J_BEATS_I,
			CommonConstants.VOTE_CHOICE_NEITHER_BEAT);

	Map<Integer, ElGamalCiphertext> REENCRYPTED_CHOICE_MAP = ConstructTestData
			.constructTestData(VOTE_CHOICES,
					(choice) -> new ElGamalCiphertext(
							CIPHERTEXT_LIST.get(choice).a
									.modMultiply(BIGINT_G.modPow(FACTOR_E, BIGINT_P), BIGINT_P),
							CIPHERTEXT_LIST.get(choice).b
									.modMultiply(PUBKEY_E.modPow(FACTOR_E, BIGINT_P), BIGINT_P)));

	CivitasBigInteger REENCRYPTED_WELL_KNOWN_CHOICE_A = CIPHERTEXT_LIST
			.get(MY_CHOICE).a
			.modMultiply(BIGINT_G.modPow(FACTOR_E, BIGINT_P), BIGINT_P);
	CivitasBigInteger REENCRYPTED_WELL_KNOWN_CHOICE_B = CIPHERTEXT_LIST
			.get(MY_CHOICE).b
			.modMultiply(PUBKEY_E.modPow(FACTOR_E, BIGINT_P), BIGINT_P);

	ElGamalCiphertext REENCRYPTED_WELL_KNOWN_CHOICE = new ElGamalCiphertext(
			REENCRYPTED_WELL_KNOWN_CHOICE_A, REENCRYPTED_WELL_KNOWN_CHOICE_B);

	CivitasBigInteger w = (FACTOR_E.modNegate(BIGINT_Q)
			.modMultiply(DS.get(MY_CHOICE), BIGINT_Q))
			.modAdd(RS.get(MY_CHOICE), BIGINT_Q);

	String EL_GAMAL_PROOF_1_OF_L_HASH_BASE64 = "TqT++ODFynAUZo5awzSUsEXBXCS6HlC2CkKk6WEG1e4=";
	CivitasBigInteger EL_GAMAL_PROOF_1_OF_L_HASH = Util
			.asBigint(EL_GAMAL_PROOF_1_OF_L_HASH_BASE64);

	CivitasBigInteger EL_GAMAL_PROOF_1_OF_L_XML_DV = EL_GAMAL_PROOF_1_OF_L_HASH
			.modSubtract(SUM, BIGINT_Q);
	CivitasBigInteger EL_GAMAL_PROOF_1_OF_L_XML_RV = w.modAdd(
			FACTOR_E.modMultiply(EL_GAMAL_PROOF_1_OF_L_XML_DV, BIGINT_Q), BIGINT_Q);

	public static final List<CivitasBigInteger> DVS = ((Supplier<List<CivitasBigInteger>>) () -> {
		List<CivitasBigInteger> d = new ArrayList<>(DS);
		d.set(MY_CHOICE, EL_GAMAL_PROOF_1_OF_L_XML_DV);
		return d;
	}).get();

	public static final List<CivitasBigInteger> RVS = ((Supplier<List<CivitasBigInteger>>) () -> {
		List<CivitasBigInteger> r = new ArrayList<>(RS);
		r.set(MY_CHOICE, EL_GAMAL_PROOF_1_OF_L_XML_RV);
		return r;
	}).get();

	public static final List<CivitasBigInteger> DVS_BAD = ((Supplier<List<CivitasBigInteger>>) () -> {
		List<CivitasBigInteger> d = new ArrayList<>(DVS);
		d.set(MY_CHOICE, BIGINT_D);
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

	String EL_GAMAL_PROOF_1_OF_L_XML_DV_BASE64 = Util
			.fromBigInt(EL_GAMAL_PROOF_1_OF_L_XML_DV);
	String EL_GAMAL_PROOF_1_OF_L_XML_RV_BASE64 = Util
			.fromBigInt(EL_GAMAL_PROOF_1_OF_L_XML_RV);

	// @formatter:off
	public static final String EL_GAMAL_PROOF_1_OF_L_XML = "<elGamalProof1OfL>"
			+ "<size>4</size>"
			+ "<dv>" + RANDOMS_BASE64.get(0) + "</dv>"
			+ "<dv>" + EL_GAMAL_PROOF_1_OF_L_XML_DV_BASE64 + "</dv>"
			+ "<dv>" + RANDOMS_BASE64.get(2) + "</dv>"
			+ "<dv>" + RANDOMS_BASE64.get(3) + "</dv>"
			+ "<rv>" + RANDOMS_BASE64.get(4) + "</rv>"
			+ "<rv>" + EL_GAMAL_PROOF_1_OF_L_XML_RV_BASE64 + "</rv>"
			+ "<rv>" + RANDOMS_BASE64.get(6) + "</rv>"
			+ "<rv>" + RANDOMS_BASE64.get(7) + "</rv>"
			+ "</elGamalProof1OfL>";
	// @formatter:on

	String REENCRYPTED_WELL_KNOWN_CHOICE_A_BASE64 = Util
			.fromBigInt(REENCRYPTED_WELL_KNOWN_CHOICE_A);
	String REENCRYPTED_WELL_KNOWN_CHOICE_B_BASE64 = Util
			.fromBigInt(REENCRYPTED_WELL_KNOWN_CHOICE_B);
	String REENCRYPTED_WELL_KNOWN_CHOICE_XML = "<ElGamalCiphertext><a>"
			+ REENCRYPTED_WELL_KNOWN_CHOICE_A_BASE64 + "</a><b>"
			+ REENCRYPTED_WELL_KNOWN_CHOICE_B_BASE64 + "</b></ElGamalCiphertext>";

	public static final String EL_GAMAL_1_OF_L_REENCRYPTION_XML = "<elGamal1OfLReencryption>"
			+ REENCRYPTED_WELL_KNOWN_CHOICE_XML + EL_GAMAL_PROOF_1_OF_L_XML
			+ "</elGamal1OfLReencryption>";
	public static final String EL_GAMAL_1_OF_L_REENCRYPTION_NULL_XML = "<elGamal1OfLReencryption></elGamal1OfLReencryption>";

	public static final ElGamal1OfLReencryption EL_GAMAL_1_OF_L_REENCRYPTION = new ElGamal1OfLReencryption(
			REENCRYPTED_WELL_KNOWN_CHOICE, EL_GAMAL_PROOF_1_OF_L);

	Map<Integer, ElGamalProof1OfL> EL_GAMAL_PROOF_1_OF_L_MAP = ConstructTestData
			.constructTestData(VOTE_CHOICES,
					(i) -> DI.get(ConstructElGamalProof1OfL.class).apply(
							EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST,
							NO_OF_WELL_KNOWN_CIPHERTEXTS, i, REENCRYPTED_CHOICE_MAP.get(i),
							ELGAMAL_REENCRYPT_FACTOR_E));
	Map<Integer, ElGamal1OfLReencryption> EL_GAMAL_1_OF_L_REENCRYPTION_MAP = ConstructTestData
			.constructTestData(VOTE_CHOICES,
					(choice) -> new ElGamal1OfLReencryption(
							REENCRYPTED_CHOICE_MAP.get(choice),
							EL_GAMAL_PROOF_1_OF_L_MAP.get(choice)));

	List<ElGamalCiphertext> REENCRYPTED_VOTE_CAPABILITIES = ENCRYPTED_VOTE_CAPABILITIES
			.stream()
			.map(x -> DI.get(ElGamalReencrypt.class).apply(EL_GAMAL_PUBLIC_KEY_EPRIME,
					x, ELGAMAL_REENCRYPT_FACTOR_EPRIME))
			.toList();

}
