package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.crypto.CryptoException;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.concrete.ElGamal1OfLReencryptionCTestData;
import civitas.crypto.concrete.ElGamalProof1OfLC;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.crypto.concrete.ElGamalReencryptFactorC;
import civitas.crypto.concrete.ElGamalReencryptFactorCTestData;
import civitas.util.DI;

public class ElGamalProof1OfLCTest implements ElGamal1OfLReencryptionCTestData,
		ElGamalReencryptFactorCTestData {

	@Test
	@DisplayName("construct and verify works" + "FIXME: move and analyze")
	void test1() throws CryptoException {
		ElGamalCiphertext[] ciphertexts = CIPHERTEXT_LIST.ciphertexts;
		ElGamalReencryptFactorC factor = ELGAMAL_REENCRYPT_FACTOR_D;
		ElGamalPublicKeyC key = EL_GAMAL_PUBLIC_KEY;
		int choice = MY_CHOICE;
		int L = 4;

		assertEquals(EL_GAMAL_CIPHERTEXT_1_OF_L_XML,
				EL_GAMAL_CIPHERTEXT_1_OF_L.toXML());
		TestUtil.setUpFakeRandom();
		ElGamalProof1OfLC proof = factory.constructElGamalProof1OfL(key,
				ciphertexts, L, choice, EL_GAMAL_CIPHERTEXT_1_OF_L, factor);
		TestUtil.tearDownFakeRandom();
		assertEquals(EL_GAMAL_PROOF_1_OF_L_XML, proof.toXML());
		VerifyElGamalProof1OfLC verifyElGamalProof1OfLC = DI
				.get(VerifyElGamalProof1OfLC.class);
		assertTrue(verifyElGamalProof1OfLC.apply(proof, key, CIPHERTEXT_LIST, L,
				EL_GAMAL_CIPHERTEXT_1_OF_L));
	}

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() throws CryptoException {

		assertEquals(EL_GAMAL_PROOF_1_OF_L_XML, EL_GAMAL_PROOF_1_OF_L.toXML());
	}

}
