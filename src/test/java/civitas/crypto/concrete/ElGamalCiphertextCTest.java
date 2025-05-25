package civitas.crypto.concrete;

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

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.algorithms.ElGamalCiphertextFromXML;
import civitas.util.Use;

public class ElGamalCiphertextCTest extends ConcreteTestBase
		implements ElGamalCiphertextCTestData {

	@Use
	ElGamalCiphertextFromXML elGamalCiphertextFromXML;

	@Test
	@DisplayName("toXML converts it to xml representation")
	void test() {
		ElGamalCiphertextC el = new ElGamalCiphertextC(BIGINT_A, BIGINT_B);
		assertEquals(XML_ELGAMALCIPHERTEXT_, el.toXML());
	}

	@Test
	@DisplayName("toXML converts it to xml representation, skipping a if it is null")
	void test1() {
		ElGamalCiphertextC el = new ElGamalCiphertextC(null, BIGINT_B);
		assertEquals(XML_ELGAMALCIPHERTEXT_ANULL, el.toXML());
	}

	@Test
	@DisplayName("toXML converts it to xml representation, skipping b if it is null")
	void test2() {
		ElGamalCiphertextC el = new ElGamalCiphertextC(BIGINT_B, null);
		assertEquals(XML_ELGAMALCIPHERTEXT_BNULL, el.toXML());
	}

	@Test
	@DisplayName("fromXML creates a new ElGamalCiphertextC")
	void test3() throws IllegalArgumentException, IOException {
		Reader r = new StringReader(XML_ELGAMALCIPHERTEXT_);
		ElGamalCiphertextC el = elGamalCiphertextFromXML.apply(r);
		assertEquals(new ElGamalCiphertextC(BIGINT_A, BIGINT_B), el);
	}

	@Test
	@DisplayName("fromXML leaves a as null if it is the empty string in the xml"
			+ "FIXME: equals blows on nulls in the original implementation")
	void test3_1() throws IllegalArgumentException, IOException {
		Reader r = new StringReader(XML_ELGAMALCIPHERTEXT_ANULL);
		ElGamalCiphertextC el = elGamalCiphertextFromXML.apply(r);
		assertEquals(new ElGamalCiphertextC(null, BIGINT_B), el);
	}

	@Test
	@DisplayName("fromXML leaves b as null if it is the empty string in the xml")
	void test3_2() throws IllegalArgumentException, IOException {
		Reader r = new StringReader(XML_ELGAMALCIPHERTEXT_BNULL);
		ElGamalCiphertextC el = elGamalCiphertextFromXML.apply(r);
		assertEquals(new ElGamalCiphertextC(BIGINT_B, null), el);
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
		assertFalse(new ElGamalCiphertextC(BIGINT_A, BIGINT_B)
				.equals(mock(ElGamalCiphertext.class)));
	}

	@Test
	@DisplayName("ElGamalCiphertextC instances with different a do not equal")
	void test7() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalCiphertextC(BIGINT_A, BIGINT_B)
				.equals(new ElGamalCiphertextC(BIGINT_B, BIGINT_B)));
	}

	@Test
	@DisplayName("ElGamalCiphertextC instances with different b do not equal")
	void test8() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalCiphertextC(BIGINT_A, BIGINT_B)
				.equals(new ElGamalCiphertextC(BIGINT_A, BIGINT_A)));
	}

	@Test
	@DisplayName("hashCode is the xor of the hashcodes of a and b")
	void test9() throws IllegalArgumentException, IOException {
		int hashcodeA = BIGINT_A.hashCode();
		int hashcodeB = BIGINT_B.hashCode();
		assertEquals(hashcodeA ^ hashcodeB,
				new ElGamalCiphertextC(BIGINT_A, BIGINT_B).hashCode());
	}

	@Test
	@DisplayName("equals works okay if a is null in one but not in the other")
	void test11() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalCiphertextC(null, BIGINT_B)
				.equals(new ElGamalCiphertextC(BIGINT_A, BIGINT_B)));
	}

	@Test
	@DisplayName("equals works okay if both a and b are null")
	void test1_2() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalCiphertextC(null, null)
				.equals(new ElGamalCiphertextC(null, BIGINT_B)));
	}

	@Test
	@DisplayName("equals works okay if everything is null")
	void test1_3() throws IllegalArgumentException, IOException {
		assertTrue(new ElGamalCiphertextC(null, null)
				.equals(new ElGamalCiphertextC(null, null)));
	}

	@Test
	@DisplayName("equals works okay if b is null in one but not in the other")
	void test12() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalCiphertextC(BIGINT_A, null)
				.equals(new ElGamalCiphertextC(BIGINT_A, BIGINT_B)));
	}

}
