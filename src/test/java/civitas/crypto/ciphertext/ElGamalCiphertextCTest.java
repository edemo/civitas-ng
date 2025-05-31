package civitas.crypto.ciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

public class ElGamalCiphertextCTest extends TestBase
		implements ElGamalCiphertextCTestData {

	@Use
	ElGamalCiphertextFromXML elGamalCiphertextFromXML;
	@Use
	ElGamalCiphertextToXML elGamalCiphertextToXML;

	@Test
	@DisplayName("toXML converts it to xml representation")
	void test() {
		ElGamalCiphertext el = new ElGamalCiphertext(BIGINT_A, BIGINT_B);
		assertEquals(XML_ELGAMALCIPHERTEXT_, elGamalCiphertextToXML.apply(el));
	}

	@Test
	@DisplayName("fromXML creates a new ElGamalCiphertextC")
	void test3() throws IllegalArgumentException, IOException {
		Reader r = new StringReader(XML_ELGAMALCIPHERTEXT_);
		ElGamalCiphertext el = elGamalCiphertextFromXML.apply(r);
		assertEquals(new ElGamalCiphertext(BIGINT_A, BIGINT_B), el);
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
	@DisplayName("does not equal to anything not ElGamalCiphertextC")
	void test6() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalCiphertext(BIGINT_A, BIGINT_B)
				.equals(mock(ElGamalCiphertext.class)));
	}

	@Test
	@DisplayName("ElGamalCiphertextC instances with different a do not equal")
	void test7() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalCiphertext(BIGINT_A, BIGINT_B)
				.equals(new ElGamalCiphertext(BIGINT_B, BIGINT_B)));
	}

	@Test
	@DisplayName("ElGamalCiphertextC instances with different b do not equal")
	void test8() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalCiphertext(BIGINT_A, BIGINT_B)
				.equals(new ElGamalCiphertext(BIGINT_A, BIGINT_A)));
	}

}
