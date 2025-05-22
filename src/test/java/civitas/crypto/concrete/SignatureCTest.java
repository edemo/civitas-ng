package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SignatureCTest extends ConcreteTestBase
		implements ConcreteTestData {

	private static final String SIGNATURE_XML = "<signature>dGVzdGRhdGE=</signature>";

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
				((SignatureC) SignatureC.fromXML(new StringReader(SIGNATURE_XML)))
						.toXML());
	}

}
