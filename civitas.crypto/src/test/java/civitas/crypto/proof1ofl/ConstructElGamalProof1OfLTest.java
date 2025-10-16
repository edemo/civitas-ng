package civitas.crypto.proof1ofl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;

class ConstructElGamalProof1OfLTest extends RandomAwareTestBase implements ElGamalProof1OfLTestData {

	@InjectMocks
	ConstructElGamalProof1OfL constructElGamalProof1OfL;

	@Test
	@DisplayName(
			"""
			returns a proof that the ciphertext is a reencription of one of the well-known ciphertexts with the key and factor
			r_i, d_i random for each ciphertexts
			a_i = ((ciphertexts_i.a/m.a)^d_i) * (g^r_i) (mod p)
			b_i = ((ciphertexts_i.b/m.b)^d_i) * (key^r_i) (mod p)
			c = hash (m.a,m.b,(ciphertexts_i.a,ciphertexts_i.b,a_i,b_i) for each ciphertext (mod q)
			w = (-factor) * d_choice + r_choice (mod q)
			sum = sum d_i for each i != choice (mod p)
			dprimet = c - sum (mod q) ; rprimet = w + factor*dprimet
			dv_i = d_i for i != choice, and dprimet for r = choice
			rv_i = r_i for i != choice, and rprimet for r = choice
			return dv, rv
			""")
	void test1() {
		ElGamalProof1OfL proof = constructElGamalProof1OfL.apply(
				EL_GAMAL_PUBLIC_KEY_E,
				CIPHERTEXT_LIST,
				MY_CHOICE.ordinal(),
				REENCRYPTED_WELL_KNOWN_CHOICE,
				ELGAMAL_REENCRYPT_FACTOR_E);

		assertEquals(EL_GAMAL_PROOF_1_OF_L, proof);
	}
}
