package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ElGamal1OfLReencryption;
import civitas.crypto.algorithms.ElGamalCiphertextFromXML;
import civitas.util.Use;

public class ElGamal1OfLReencryptionCTest extends ConcreteTestBase
		implements ElGamal1OfLReencryptionCTestData {

	@Use
	ElGamalCiphertextFromXML elGamalCiphertextFromXML;

	@Test
	@DisplayName("constructor accepts nulls")
	void test0() {
		assertEquals(EL_GAMAL_1_OF_L_REENCRYPTION_NULL_XML,
				new ElGamal1OfLReencryptionC(null, null).toXML());
	}

	@Test
	@DisplayName("getCyphertext works as expected")
	void test() {
		assertEquals(EL_GAMAL_CIPHERTEXT_1_OF_L_XML,
				((ElGamalCiphertextC) EL_GAMAL_1_OF_L_REENCRYPTION.getCiphertext())
						.toXML());
	}

	@Test
	@DisplayName("getProof works as expected")
	void getProofTest() {
		assertEquals(EL_GAMAL_PROOF_1_OF_L_XML,
				((ElGamalProof1OfLC) EL_GAMAL_1_OF_L_REENCRYPTION.getProof()).toXML());
	}

	@Test
	@DisplayName("toXML works as expected")
	void toXMLTest() {
		assertEquals(EL_GAMAL_1_OF_L_REENCRYPTION_XML,
				EL_GAMAL_1_OF_L_REENCRYPTION.toXML());
	}

	@Test
	@DisplayName("equals when the message and the proof are the same ")
	void testEquals() throws IllegalArgumentException, IOException {
		assertTrue(ElGamal1OfLReencryptionC
				.fromXML(new StringReader(EL_GAMAL_1_OF_L_REENCRYPTION_XML))
				.equals(EL_GAMAL_1_OF_L_REENCRYPTION));
	}

	@Test
	@DisplayName("not equals when the message is different")
	void testEquals_1() throws IllegalArgumentException, IOException {

		assertFalse(ElGamal1OfLReencryptionC
				.fromXML(new StringReader(EL_GAMAL_1_OF_L_REENCRYPTION_XML))
				.equals(new ElGamal1OfLReencryptionC(
						elGamalCiphertextFromXML
								.apply(new StringReader(EL_GAMAL_CIPHERTEXT_NAIVE_XML)),
						ElGamalProof1OfLC
								.fromXML(new StringReader(EL_GAMAL_PROOF_1_OF_L_XML)))));
	}

	@Test
	@DisplayName("not equals when the proof is different")
	void testEquals_2() throws IllegalArgumentException, IOException {

		assertFalse(ElGamal1OfLReencryptionC
				.fromXML(new StringReader(EL_GAMAL_1_OF_L_REENCRYPTION_XML))
				.equals(new ElGamal1OfLReencryptionC(EL_GAMAL_CIPHERTEXT_1_OF_L,
						EL_GAMAL_PROOF_1_OF_L_BAD)));
	}

	@Test
	@DisplayName("reencryption containing null is not equal to anything")
	void testEquals1() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamal1OfLReencryptionC(null, null)
				.equals(new ElGamal1OfLReencryptionC(null, null)));
	}

	@Test
	@DisplayName("not equals to anything not ElGamal1OfLReencryptionC")
	void testEquals2() throws IllegalArgumentException, IOException {
		assertFalse(EL_GAMAL_1_OF_L_REENCRYPTION
				.equals(mock(ElGamal1OfLReencryption.class)));
	}

}
