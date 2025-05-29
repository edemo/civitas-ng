package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.importing.PublicKeyCiphertextFromXML;
import civitas.util.Use;

public class PublicKeyCiphertextCTest extends ConcreteTestBase
		implements ConcreteTestData {
	@Use
	PublicKeyCiphertextFromXML publicKeyCiphertextFromXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PUBLIC_KEY_CIPHERTEXT_XML,
				new PublicKeyCiphertextC(BYTES).toXML());
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test1() throws IllegalArgumentException, IOException {

		assertEquals(PUBLIC_KEY_CIPHERTEXT_XML,
				((PublicKeyCiphertextC) publicKeyCiphertextFromXML
						.apply(new StringReader(PUBLIC_KEY_CIPHERTEXT_XML))).toXML());
	}

}
