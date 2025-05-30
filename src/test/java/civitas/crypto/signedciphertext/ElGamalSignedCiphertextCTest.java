package civitas.crypto.signedciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.util.Use;

public class ElGamalSignedCiphertextCTest extends ConcreteTestBase
		implements ElGamalSignedCiphertextCTestData {

	@Use
	ElGamalSignedCiphertextFromXML elGamalSignedCiphertextFromXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(EL_GAMAL_SIGNED_CIPHERTEXT_XML,
				EL_GAMAL_SIGNED_CIPHERTEXT.toXML());
	}

	@Test
	@DisplayName("constructor accepts nulls")
	void test2() {
		assertEquals(EL_GAMAL_SIGNED_CIPHERTEXT_NULL_XML,
				new ElGamalSignedCiphertextC(null, null, null, null).toXML());
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test1() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_SIGNED_CIPHERTEXT, elGamalSignedCiphertextFromXML
				.apply(new StringReader(EL_GAMAL_SIGNED_CIPHERTEXT_XML)));

	}

	@Test
	@DisplayName("fromXML with null parameters results in null parameters")
	void test3() throws IllegalArgumentException, IOException {
		assertTrue(new ElGamalSignedCiphertextC(null, null, null, null)
				.equals(elGamalSignedCiphertextFromXML
						.apply(new StringReader(EL_GAMAL_SIGNED_CIPHERTEXT_NULL_XML))));

	}

}
