package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PublicKeyCiphertextCTest extends ConcreteTestBase {

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
				((PublicKeyCiphertextC) PublicKeyCiphertextC
						.fromXML(new StringReader(PUBLIC_KEY_CIPHERTEXT_XML))).toXML());
	}

}
