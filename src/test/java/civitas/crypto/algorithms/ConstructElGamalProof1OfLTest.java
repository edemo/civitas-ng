package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoException;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamal1OfLReencryptionCTestData;
import civitas.crypto.concrete.ElGamalProof1OfLC;
import civitas.crypto.concrete.ElGamalReencryptFactorCTestData;
import civitas.util.Tested;

public class ConstructElGamalProof1OfLTest extends ConcreteTestBase implements
		ElGamal1OfLReencryptionCTestData, ElGamalReencryptFactorCTestData {

	@Tested
	ConstructElGamalProof1OfL constructElGamalProof1OfL;

	@Test
	@DisplayName("returns a proof of knowledge of ciphertext m"
			+ "r_i, d_i random for each ciphertexts"
			+ "a_i = ((ciphertexts_i.a/m.a)^d_i) * (g^r_i) (mod p)"
			+ "b_i = ((ciphertexts_i.b/m.b)^d_i) * (key^r_i) (mod p)"
			+ "c = hash (m.a,m.b,(ciphertexts_i.a,ciphertexts_i.b,a_i,b_i) for each ciphertext (mod q)"
			+ "w = (-factor) * d_choice + r_choice (mod q)"
			+ "sum = sum d_i for each i != choice (mod p)"
			+ "dprimet = c - sum (mod q) ; rprimet = w + factor*dprimet"
			+ "dv_i = d_i for i != choice, and dprimet for r = choice"
			+ "rv_i = r_i for i != choice, and rprimet for r = choice"
			+ "return dv, rv")
	void test1() throws CryptoException {
		ElGamalProof1OfLC proof = constructElGamalProof1OfL.apply(
				EL_GAMAL_PUBLIC_KEY, CIPHERTEXT_LIST.ciphertexts,
				NO_OF_WELL_KNOWN_CIPHERTEXTS, MY_CHOICE, EL_GAMAL_CIPHERTEXT_1_OF_L,
				ELGAMAL_REENCRYPT_FACTOR_D);
		assertEquals(EL_GAMAL_PROOF_1_OF_L_XML, proof.toXML());
	}

}
