package civitas.crypto.signedciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

public class ElGamalSignedCiphertextCTest extends TestBase
		implements ElGamalSignedCiphertextCTestData {

	@Use
	ElGamalSignedCiphertextFromXML elGamalSignedCiphertextFromXML;
	@Use
	ElGamalSignedCiphertextToXML elGamalSignedCiphertextToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(EL_GAMAL_SIGNED_CIPHERTEXT_XML,
				elGamalSignedCiphertextToXML.apply(EL_GAMAL_SIGNED_CIPHERTEXT));
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test1() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_SIGNED_CIPHERTEXT, elGamalSignedCiphertextFromXML
				.apply(new StringReader(EL_GAMAL_SIGNED_CIPHERTEXT_XML)));

	}

}
