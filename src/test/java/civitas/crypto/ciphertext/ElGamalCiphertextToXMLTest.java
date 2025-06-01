package civitas.crypto.ciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

public class ElGamalCiphertextToXMLTest extends TestBase
		implements ElGamalCiphertextTestData {

	@Use
	ElGamalCiphertextToXML elGamalCiphertextToXML;

	@Test
	@DisplayName("toXML converts it to xml representation")
	void test() {
		ElGamalCiphertext el = new ElGamalCiphertext(BIGINT_A, BIGINT_B);
		assertEquals(ELGAMALCIPHERTEXT_XML, elGamalCiphertextToXML.apply(el));
	}

}
