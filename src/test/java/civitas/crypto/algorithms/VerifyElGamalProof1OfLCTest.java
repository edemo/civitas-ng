package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoException;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamal1OfLReencryptionCTestData;
import civitas.crypto.concrete.ElGamalReencryptFactorCTestData;
import civitas.util.Tested;
import civitas.util.Use;

public class VerifyElGamalProof1OfLCTest extends ConcreteTestBase implements
		ElGamal1OfLReencryptionCTestData, ElGamalReencryptFactorCTestData {

	@Tested
	VerifyElGamalProof1OfLC verifyElGamalProof1OfLC;
	@Tested
	ConstructElGamalProof1OfL constructElGamalProof1OfL;
	@Use
	ConvertHashToBigInt convertHashToBigInt;
	@Use
	CryptoHash cryptoHash;
	@Use
	ElGamalReencrypt elGamalReencrypt;

	@Test
	@DisplayName("verifies that the prover reencrypted one of the cyphertexts to the msg using pubkey"
			+ "a_i = (ciphertexts_i.a/m.a)^dv_i * g^ rv_i (mod p) "
			+ "b_i = (ciphertexts_i.b/m.b)^dv_i * key^ rv_i (mod p) "
			+ "c = hash(m.a,m.b,(ciphertexts_i.a,ciphertexts_i.b,a_i,b_i) for each ciphertexts ) "
			+ "sum = sum dv_i (mod q)                        "
			+ "verify that sum = c")
	void test1() throws CryptoException {

		assertTrue(verifyElGamalProof1OfLC.apply(EL_GAMAL_PROOF_1_OF_L,
				EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, NO_OF_WELL_KNOWN_CIPHERTEXTS,
				REENCRYPTED_WELL_KNOWN_CHOICE));
	}

	@Test
	@DisplayName("fails if the length given is not matching the length of the proof")
	void test2() throws CryptoException {
		assertFalse(verifyElGamalProof1OfLC.apply(EL_GAMAL_PROOF_1_OF_L,
				EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST, 1,
				REENCRYPTED_WELL_KNOWN_CHOICE));
	}

}
