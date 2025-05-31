package civitas.crypto.oneoflreencryption;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextCTestData;
import civitas.crypto.proof1ofl.ElGamalProof1OfLC;
import civitas.util.CivitasBigInteger;

public interface ElGamal1OfLReencryptionCTestData
		extends ElGamalCiphertextCTestData {

	public static final int MY_CHOICE = 2;

	List<CivitasBigInteger> DS = RANDOMS.subList(0, NO_OF_WELL_KNOWN_CIPHERTEXTS);
	List<CivitasBigInteger> RS = RANDOMS.subList(NO_OF_WELL_KNOWN_CIPHERTEXTS,
			NO_OF_WELL_KNOWN_CIPHERTEXTS * 2);

	CivitasBigInteger SUM = DS.stream().reduce(ZERO, (a, b) -> {
		if (b != DS.get(MY_CHOICE))
			return a.modAdd(b, BIGINT_Q);
		return a;
	});

	String EL_GAMAL_PROOF_1_OF_L_HASH_BASE64 = "NuStU6Al5jmF55/oKAee88s4Zpv/jGxgxfoLGtq3s6w=";
	CivitasBigInteger EL_GAMAL_PROOF_1_OF_L_HASH = Util
			.asBigint(EL_GAMAL_PROOF_1_OF_L_HASH_BASE64);
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

	public static final ElGamalProof1OfLC EL_GAMAL_PROOF_1_OF_L = new ElGamalProof1OfLC(
			NO_OF_WELL_KNOWN_CIPHERTEXTS,
			DVS.toArray(new CivitasBigInteger[NO_OF_WELL_KNOWN_CIPHERTEXTS]),
			RVS.toArray(new CivitasBigInteger[NO_OF_WELL_KNOWN_CIPHERTEXTS]));

	public static final ElGamalProof1OfLC EL_GAMAL_PROOF_1_OF_L_BAD = new ElGamalProof1OfLC(
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
			+ "<dv>" + RANDOMS_BASE64.get(1) + "</dv>"
			+ "<dv>" + EL_GAMAL_PROOF_1_OF_L_XML_DV_BASE64 + "</dv>"
			+ "<dv>" + RANDOMS_BASE64.get(3) + "</dv>"
			+ "<rv>" + RANDOMS_BASE64.get(4) + "</rv>"
			+ "<rv>" + RANDOMS_BASE64.get(5) + "</rv>"
			+ "<rv>" + EL_GAMAL_PROOF_1_OF_L_XML_RV_BASE64 + "</rv>"
			+ "<rv>" + RANDOMS_BASE64.get(7) + "</rv>"
			+ "</elGamalProof1OfL>";
	// @formatter:on

	String REENCRYPTED_WELL_KNOWN_CHOICE_A_BASE64 = Util
			.fromBigInt(REENCRYPTED_WELL_KNOWN_CHOICE_A);
	String REENCRYPTED_WELL_KNOWN_CHOICE_B_BASE64 = Util
			.fromBigInt(REENCRYPTED_WELL_KNOWN_CHOICE_B);
	String REENCRYPTED_WELL_KNOWN_CHOICE_XML = "<elGamalCiphertext><a>"
			+ REENCRYPTED_WELL_KNOWN_CHOICE_A_BASE64 + "</a><b>"
			+ REENCRYPTED_WELL_KNOWN_CHOICE_B_BASE64 + "</b></elGamalCiphertext>";

	public static final String EL_GAMAL_1_OF_L_REENCRYPTION_XML = "<elGamal1OfLReencryption>"
			+ EL_GAMAL_CIPHERTEXT_E_XML + EL_GAMAL_PROOF_1_OF_L_XML
			+ "</elGamal1OfLReencryption>";
	public static final String EL_GAMAL_1_OF_L_REENCRYPTION_NULL_XML = "<elGamal1OfLReencryption></elGamal1OfLReencryption>";

	public static final ElGamal1OfLReencryption EL_GAMAL_1_OF_L_REENCRYPTION = new ElGamal1OfLReencryption(
			CIPHERTEXT_E, EL_GAMAL_PROOF_1_OF_L);

}
