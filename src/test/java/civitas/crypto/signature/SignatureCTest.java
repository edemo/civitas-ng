package civitas.crypto.signature;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

public class SignatureCTest extends TestBase implements SignatureTestData {

	@Use
	SignatureFromXML signatureFromXML;

	@Use
	SignatureToXML signatureToXML;

	@Test
	@DisplayName("constructor and toXML wors as expected")
	void test() {
		assertEquals(SIGNATURE_XML, signatureToXML.apply(new Signature(BYTES)));
	}

	@Test
	@DisplayName("signature contains the bytes")
	void test1() {
		assertArrayEquals(BYTES, SIGNATURE.signature);
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(SIGNATURE,
				(signatureFromXML.apply(new StringReader(SIGNATURE_XML))));
	}

}
