package civitas.crypto.ciphertextlist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

class CiphertextListfromXMLTest extends TestBase
		implements ElGamalCiphertextListTestData {

	@Use
	CiphertextListfromXML ciphertextListfromXML;

	@Test
	@DisplayName("converts the xml to a CiphertextList")
	void test7() throws IllegalArgumentException, IOException {
		assertEquals(CIPHERTEXTLIST_ONEINSIDE,
				ciphertextListfromXML.apply(new StringReader(CIPHERTEXT_LIST_AS_XML)));
	}

	@Test
	@DisplayName("converts the xml of an empty CiphertextList correctly")
	void test8() throws IllegalArgumentException, IOException {
		assertEquals(CIPHERTEXTLIST_ZEROSIZED, ciphertextListfromXML
				.apply(new StringReader(EMPTY_CIPHERTEXT_LIST_AS_XML)));
	}

	@Test
	@DisplayName("converts the xml with a negative length to an empthy list")
	void test9() throws IllegalArgumentException, IOException {
		StringReader sr = new StringReader(
				CIPHERTEXT_LIST_XML_WITH_NEGATIVE_LENGTH);
		assertEquals(CIPHERTEXTLIST_ZEROSIZED, ciphertextListfromXML.apply(sr));
	}

}
