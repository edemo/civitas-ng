package civitas.crypto.ciphertextlist;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

public class CiphertextListTest extends TestBase
		implements ElGamalCiphertextListTestData {
	@Use
	CiphertextListToXML ciphertextListToXML;
	@Use
	ConstructCiphertextList constructCiphertextList;

	@Test
	@DisplayName("get(n) gets the nth cypertext")
	void test3() {
		assertEquals(CIPHERTEXT_E, CIPHERTEXTLIST_ONEINSIDE.get(0));
	}

	@Test
	@DisplayName("get(n) throws IndexOutOfBoundsException for out of bounds index")
	void test4() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> CIPHERTEXTLIST_ONEINSIDE.get(1));
	}

	@Test
	@DisplayName("toXml returns an xml representation of the list")
	void test6() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ciphertextListToXML.apply(CIPHERTEXTLIST_ONEINSIDE, printWriter);
		assertEquals(CIPHERTEXT_LIST_AS_XML, stringWriter.toString());
	}

	@Test
	@DisplayName("toXml returns correct xml for an empty one")
	void test6_1() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ciphertextListToXML.apply(CIPHERTEXTLIST_ZEROSIZED, printWriter);

		assertEquals(EMPTY_CIPHERTEXT_LIST_AS_XML, stringWriter.toString());
	}

	@Test
	@DisplayName("toXml does nothing silently if printwriter is null")
	void test6_3() {
		assertDoesNotThrow(
				() -> ciphertextListToXML.apply(CIPHERTEXTLIST_ZEROSIZED, null));

	}

}
