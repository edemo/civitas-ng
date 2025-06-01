package civitas.crypto.ciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertextTestData;
import civitas.util.Tested;

public class ElGamalCiphertextFromXMLTest extends TestBase
		implements ElGamalCiphertextTestData, ElGamalSignedCiphertextTestData {

	@Tested
	ElGamalCiphertextFromXML elGamalCiphertextFromXML;

	@Test
	@DisplayName("fromXML creates a new ElGamalCiphertext from an xml corresponding to that")
	void test3() throws IllegalArgumentException, IOException {
		Reader r = new StringReader(EL_GAMAL_CIPHERTEXT_XML);
		ElGamalCiphertext el = elGamalCiphertextFromXML.apply(r);
		assertEquals(EL_GAMAL_CIPHERTEXT, el);
	}

	@Test
	@DisplayName("fromXML creates a new ElGamalSignedCiphertext from an xml corresponding to that")
	void test3_1() throws IllegalArgumentException, IOException {
		Reader r = new StringReader(EL_GAMAL_SIGNED_CIPHERTEXT_XML);
		ElGamalCiphertext el = elGamalCiphertextFromXML.apply(r);
		assertEquals(EL_GAMAL_SIGNED_CIPHERTEXT, el);
	}

	@Test
	@DisplayName("fromXML with reader null throws IllegalArgumentException")
	void test4() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> elGamalCiphertextFromXML.apply(null));
	}

	@Test
	@DisplayName("fromXML with truncated xml  throws IOException")
	void test5() throws IllegalArgumentException, IOException {
		Reader r = new StringReader(XML_ELGAMALCIPHERTEXT_TRUNCATED);
		assertThrows(IOException.class, () -> elGamalCiphertextFromXML.apply(r));
	}

	@Test
	@DisplayName("fromXML with empty parameter throws Exception")
	void test6() throws IllegalArgumentException, IOException {
		Reader r = new StringReader(XML_ELGAMALCIPHERTEXT_ANULL);
		assertThrows(NumberFormatException.class,
				() -> elGamalCiphertextFromXML.apply(r));
	}

}
