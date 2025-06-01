package civitas.crypto.decriptionshare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

public class ElGamalDecryptionShareCTest extends TestBase
		implements ElGamalDecryptionShareTestData {

	@Use
	ElGamalDecryptionShareFromXML elGamalDecryptionShareFromXML;
	@Use
	ElGamalDecryptionShareToXML elGamalDecryptionShareToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(EL_GAMAL_DECRYPTION_SHARE_XML,
				elGamalDecryptionShareToXML.apply(EL_GAMAL_DECRYPTION_SHARE));
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_DECRYPTION_SHARE_XML,
				elGamalDecryptionShareToXML.apply(elGamalDecryptionShareFromXML
						.apply(new StringReader(EL_GAMAL_DECRYPTION_SHARE_XML))));
	}

	@Test
	@DisplayName("getProof gets the proof")
	void test3() {
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECRIPTIONSHARE,
				EL_GAMAL_DECRYPTION_SHARE.proof);
	}

}
