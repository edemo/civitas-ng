package civitas.crypto.ciphertext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.ciphertextlist.CiphertextListToXML;
import civitas.crypto.ciphertextlist.CiphertextListfromXML;
import civitas.crypto.ciphertextlist.ConstructCiphertextList;
import civitas.util.Use;

public class CiphertextListTest extends TestBase
		implements ElGamalCiphertextTestData {
	@Use
	CiphertextListfromXML ciphertextListfromXML;
	@Use
	CiphertextListToXML ciphertextListToXML;
	@Use
	ConstructCiphertextList constructCiphertextList;

	CiphertextList zerosized = new CiphertextList();
	CiphertextList nullInside = new CiphertextList(
			Arrays.asList(new ElGamalCiphertext[] { null }));

	CiphertextList oneInside = new CiphertextList(
			Arrays.asList(new ElGamalCiphertext[] { CIPHERTEXT_E }));

	@Test
	@DisplayName("get(n) gets the nth cypertext")
	void test3() {
		assertEquals(CIPHERTEXT_E, oneInside.get(0));
	}

	@Test
	@DisplayName("get(n) throws IndexOutOfBoundsException for out of bounds index")
	void test4() {
		assertThrows(IndexOutOfBoundsException.class, () -> oneInside.get(1));
	}

	@Test
	@DisplayName("toXml returns an xml representation of the list")
	void test6() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ciphertextListToXML.apply(oneInside, printWriter);
		assertEquals(CIPHERTEXT_LIST_AS_XML, stringWriter.toString());
	}

	@Test
	@DisplayName("toXml returns correct xml for an empty one")
	void test6_1() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ciphertextListToXML.apply(zerosized, printWriter);

		assertEquals(EMPTY_CIPHERTEXT_LIST_AS_XML, stringWriter.toString());
	}

	@Test
	@DisplayName("toXml returns correct xml for one with null inside"
			+ "FIXME: original code catched NullPointerException instead of using the computed length in the for cycle")
	void test6_2() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ciphertextListToXML.apply(nullInside, printWriter);

		assertEquals(EMPTY_CIPHERTEXT_LIST_AS_XML, stringWriter.toString());
	}

	@Test
	@DisplayName("toXml does nothing silently if printwriter is null")
	void test6_3() {
		assertDoesNotThrow(() -> ciphertextListToXML.apply(zerosized, null));

	}

	@Test
	@DisplayName("fromXML converts the xml to a CiphertextList")
	void test7() throws IllegalArgumentException, IOException {
		StringReader sr = new StringReader(CIPHERTEXT_LIST_AS_XML);
		CiphertextList cl = ciphertextListfromXML.apply(sr);
		assertEquals(CIPHERTEXT_E, cl.get(0));
		assertEquals(1, cl.size());
	}

	@Test
	@DisplayName("fromXML converts the xml of an empty CiphertextList correctly")
	void test8() throws IllegalArgumentException, IOException {
		StringReader sr = new StringReader(EMPTY_CIPHERTEXT_LIST_AS_XML);
		CiphertextList cl = ciphertextListfromXML.apply(sr);
		assertEquals(0, cl.size());
	}

	@Test
	@DisplayName("fromXML converts the xml with a negative length to an empthy list")
	void test9() throws IllegalArgumentException, IOException {
		StringReader sr = new StringReader(
				CIPHERTEXT_LIST_XML_WITH_NEGATIVE_LENGTH);
		CiphertextList cl = ciphertextListfromXML.apply(sr);
		assertEquals(0, cl.size());
	}
}
