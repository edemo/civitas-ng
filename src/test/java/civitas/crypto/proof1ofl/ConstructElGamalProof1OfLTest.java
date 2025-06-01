package civitas.crypto.proof1ofl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.common.Util;
import civitas.crypto.CryptoException;
import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionCTestData;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorCTestData;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class ConstructElGamalProof1OfLTest extends TestBase implements
		ElGamal1OfLReencryptionCTestData, ElGamalReencryptFactorCTestData {

	@Tested
	ConstructElGamalProof1OfL constructElGamalProof1OfL;
	@Use
	private ConvertHashToBigInt convertHashToBigInt;
	@Use
	private CryptoHash cryptoHash;
	@Use
	ElGamalProof1OfLToXML elGamalProof1OfLToXML;

	@Test
	@DisplayName("returns a proof that the ciphertext is a reencription of"
			+ "one of the well-known ciphertexts with the key and factor\n"
			+ "r_i, d_i random for each ciphertexts\n"
			+ "a_i = ((ciphertexts_i.a/m.a)^d_i) * (g^r_i) (mod p)\n"
			+ "b_i = ((ciphertexts_i.b/m.b)^d_i) * (key^r_i) (mod p)\n"
			+ "c = hash (m.a,m.b,(ciphertexts_i.a,ciphertexts_i.b,a_i,b_i) for each ciphertext (mod q)\n"
			+ "w = (-factor) * d_choice + r_choice (mod q)\n"
			+ "sum = sum d_i for each i != choice (mod p)\n"
			+ "dprimet = c - sum (mod q) ; rprimet = w + factor*dprimet\n"
			+ "dv_i = d_i for i != choice, and dprimet for r = choice\n"
			+ "rv_i = r_i for i != choice, and rprimet for r = choice\n"
			+ "return dv, rv")
	void test1() throws CryptoException {
		ElGamalProof1OfL proof = constructElGamalProof1OfL.apply(
				EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, NO_OF_WELL_KNOWN_CIPHERTEXTS,
				MY_CHOICE, REENCRYPTED_WELL_KNOWN_CHOICE, ELGAMAL_REENCRYPT_FACTOR_E);

		assertEquals(EL_GAMAL_PROOF_1_OF_L_XML, elGamalProof1OfLToXML.apply(proof));
	}

	@Test
	@Tag("testdata")
	@DisplayName("EL_GAMAL_PROOF_1_OF_L_HASH_BASE64")
	void checkHash() {
		CivitasBigInteger ciphertextA = REENCRYPTED_WELL_KNOWN_CHOICE_A;
		CivitasBigInteger ciphertextB = REENCRYPTED_WELL_KNOWN_CHOICE_B;
		CivitasBigInteger pubkey = PUBKEY_E;
		List<ElGamalCiphertext> ms = CIPHERTEXT_LIST.stream().map(x -> x)
				.collect(Collectors.toList());
		List<CivitasBigInteger> as = IntStream
				.range(0, NO_OF_WELL_KNOWN_CIPHERTEXTS).mapToObj(i -> {
					return ms.get(i).a.modDivide(ciphertextA, BIGINT_P)
							.modPow(DS.get(i), BIGINT_P)
							.modMultiply(BIGINT_G.modPow(RS.get(i), BIGINT_P), BIGINT_P)
							.mod(BIGINT_P);
				}).collect(Collectors.toList());
		List<CivitasBigInteger> bs = IntStream
				.range(0, NO_OF_WELL_KNOWN_CIPHERTEXTS).mapToObj(i -> {
					return ms.get(i).b.modDivide(ciphertextB, BIGINT_P)
							.modPow(DS.get(i), BIGINT_P)
							.modMultiply(pubkey.modPow(RS.get(i), BIGINT_P), BIGINT_P)
							.mod(BIGINT_P);
				}).collect(Collectors.toList());

		List<CivitasBigInteger> env = ((Supplier<List<CivitasBigInteger>>) () -> {
			ArrayList<CivitasBigInteger> d = new ArrayList<>(
					2 + 4 * NO_OF_WELL_KNOWN_CIPHERTEXTS);
			d.add(ciphertextA);
			d.add(ciphertextB);
			for (int i = 0; i < NO_OF_WELL_KNOWN_CIPHERTEXTS; i++) {
				d.add(ms.get(i).a);
				d.add(ms.get(i).b);
				d.add(as.get(i));
				d.add(bs.get(i));
			}
			return d;
		}).get();

		CivitasBigInteger hash = convertHashToBigInt.apply(cryptoHash.apply(env))
				.mod(BIGINT_Q);
		assertEquals(EL_GAMAL_PROOF_1_OF_L_HASH_BASE64, Util.fromBigInt(hash));

	}

}
