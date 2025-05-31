package civitas.crypto.oneoflreencryption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.ciphertext.ElGamalCiphertextFromXML;
import civitas.crypto.ciphertext.ElGamalCiphertextToXML;
import civitas.crypto.proof1ofl.ElGamalProof1OfLFromXML;
import civitas.util.Use;

public class ElGamal1OfLReencryptionCTest extends TestBase
		implements ElGamal1OfLReencryptionCTestData {

	@Use
	ElGamalCiphertextFromXML elGamalCiphertextFromXML;
	@Use
	ElGamalCiphertextToXML elGamalCiphertextToXML;
	@Use
	ElGamal1OfLReencryptionFromXML elGamal1OfLReencryptionFromXML;
	@Use
	ElGamal1OfLReencryptionToXML elGamal1OfLReencryptionToXML;

	@Test
	@DisplayName("getCyphertext works as expected")
	void test() {
		assertEquals(EL_GAMAL_CIPHERTEXT_E_XML,
				elGamalCiphertextToXML.apply(EL_GAMAL_1_OF_L_REENCRYPTION.m));
	}

	@Test
	@DisplayName("getProof works as expected")
	void getProofTest() {
		assertEquals(EL_GAMAL_PROOF_1_OF_L, EL_GAMAL_1_OF_L_REENCRYPTION.proof);
	}

	@Test
	@DisplayName("toXML works as expected")
	void toXMLTest() {
		assertEquals(EL_GAMAL_1_OF_L_REENCRYPTION_XML,
				elGamal1OfLReencryptionToXML.apply(EL_GAMAL_1_OF_L_REENCRYPTION));
	}

	@Test
	@DisplayName("equals when the message and the proof are the same ")
	void testEquals() throws IllegalArgumentException, IOException {
		assertEquals(
				elGamal1OfLReencryptionFromXML
						.apply(new StringReader(EL_GAMAL_1_OF_L_REENCRYPTION_XML)),
				EL_GAMAL_1_OF_L_REENCRYPTION);
	}

	@Use
	ElGamalProof1OfLFromXML elGamalProof1OfLFromXML;

	@Test
	@DisplayName("not equals when the message is different")
	void testEquals_1() throws IllegalArgumentException, IOException {

		assertFalse(elGamal1OfLReencryptionFromXML
				.apply(new StringReader(EL_GAMAL_1_OF_L_REENCRYPTION_XML))
				.equals(new ElGamal1OfLReencryption(
						elGamalCiphertextFromXML
								.apply(new StringReader(EL_GAMAL_CIPHERTEXT_NAIVE_XML)),
						elGamalProof1OfLFromXML
								.apply(new StringReader(EL_GAMAL_PROOF_1_OF_L_XML)))));
	}

	@Test
	@DisplayName("not equals when the proof is different")
	void testEquals_2() throws IllegalArgumentException, IOException {

		assertFalse(elGamal1OfLReencryptionFromXML
				.apply(new StringReader(EL_GAMAL_1_OF_L_REENCRYPTION_XML))
				.equals(new ElGamal1OfLReencryption(CIPHERTEXT_E,
						EL_GAMAL_PROOF_1_OF_L_BAD)));
	}

	@Test
	@DisplayName("not equals to anything not ElGamal1OfLReencryptionC")
	void testEquals2() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_1_OF_L_REENCRYPTION
				.equals(mock(ElGamal1OfLReencryption.class)));
	}

}
