package civitas.crypto.ciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.util.Use;

public class ElGamalCiphertextCTest extends ConcreteTestBase
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
	@DisplayName("toXML converts it to xml representation, skipping a if it is null")
	void test1() {
		ElGamalCiphertext el = new ElGamalCiphertext(null, BIGINT_B);
		assertEquals(XML_ELGAMALCIPHERTEXT_ANULL, elGamalCiphertextToXML.apply(el));
	}

	@Test
	@DisplayName("toXML converts it to xml representation, skipping b if it is null")
	void test2() {
		ElGamalCiphertext el = new ElGamalCiphertext(BIGINT_B, null);
		assertEquals(XML_ELGAMALCIPHERTEXT_BNULL, elGamalCiphertextToXML.apply(el));
	}

	@Test
	@DisplayName("fromXML creates a new ElGamalCiphertextC")
	void test3() throws IllegalArgumentException, IOException {
		Reader r = new StringReader(XML_ELGAMALCIPHERTEXT_);
		ElGamalCiphertext el = elGamalCiphertextFromXML.apply(r);
		assertEquals(new ElGamalCiphertext(BIGINT_A, BIGINT_B), el);
	}

	@Test
	@DisplayName("fromXML leaves a as null if it is the empty string in the xml"
			+ "FIXME: equals blows on nulls in the original implementation")
	void test3_1() throws IllegalArgumentException, IOException {
		Reader r = new StringReader(XML_ELGAMALCIPHERTEXT_ANULL);
		ElGamalCiphertext el = elGamalCiphertextFromXML.apply(r);
		assertEquals(new ElGamalCiphertext(null, BIGINT_B), el);
	}

	@Test
	@DisplayName("fromXML leaves b as null if it is the empty string in the xml")
	void test3_2() throws IllegalArgumentException, IOException {
		Reader r = new StringReader(XML_ELGAMALCIPHERTEXT_BNULL);
		ElGamalCiphertext el = elGamalCiphertextFromXML.apply(r);
		assertEquals(new ElGamalCiphertext(BIGINT_B, null), el);
	}

	@Test
	@DisplayName("fromXML with reader null throws IllegalArgumentException")
	void test4() throws IllegalArgumentException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> elGamalCiphertextFromXML.apply(null));
	}

	@Test
	@DisplayName("fromXML with truncated xml null throws IOException")
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

	@Test
	@DisplayName("equals works okay if a is null in one but not in the other")
	void test11() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalCiphertext(null, BIGINT_B)
				.equals(new ElGamalCiphertext(BIGINT_A, BIGINT_B)));
	}

	@Test
	@DisplayName("equals works okay if both a and b are null")
	void test1_2() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalCiphertext(null, null)
				.equals(new ElGamalCiphertext(null, BIGINT_B)));
	}

	@Test
	@DisplayName("equals works okay if everything is null")
	void test1_3() throws IllegalArgumentException, IOException {
		assertTrue(new ElGamalCiphertext(null, null)
				.equals(new ElGamalCiphertext(null, null)));
	}

	@Test
	@DisplayName("equals works okay if b is null in one but not in the other")
	void test12() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalCiphertext(BIGINT_A, null)
				.equals(new ElGamalCiphertext(BIGINT_A, BIGINT_B)));
	}

}
