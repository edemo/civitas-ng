package civitas.crypto.proof1ofl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

public class ConstructElGamalProof1OfLTest extends TestBase
		implements ElGamalProof1OfLTestData {

	@InjectMocks
	ConstructElGamalProof1OfL constructElGamalProof1OfL;

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
	void test1() {
		ElGamalProof1OfL proof = constructElGamalProof1OfL.apply(
				EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, MY_CHOICE.ordinal(),
				REENCRYPTED_WELL_KNOWN_CHOICE, ELGAMAL_REENCRYPT_FACTOR_E);

		assertEquals(EL_GAMAL_PROOF_1_OF_L, proof);
	}

}
