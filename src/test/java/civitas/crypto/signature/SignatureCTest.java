package civitas.crypto.signature;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.util.Use;

public class SignatureCTest extends ConcreteTestBase
		implements SignatureTestData {

	@Use
	SignatureFromXML signatureFromXML;

	@Test
	@DisplayName("constructor and toXML wors as expected")
	void test() {
		assertEquals(SIGNATURE_XML, new SignatureC(BYTES).toXML());
	}

	@Test
	@DisplayName("toBytes gets the bytes")
	void test1() {
		assertArrayEquals(BYTES, new SignatureC(BYTES).toBytes());
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(SIGNATURE_XML,
				((SignatureC) signatureFromXML.apply(new StringReader(SIGNATURE_XML)))
						.toXML());
	}

}
