package civitas.crypto.proofdvr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

public class ElGamalProofDVRCTest extends TestBase
		implements ElGamalProofDVRCTestData {
	@Use
	ElGamalProofDVRFromXML elGamalProofDVRFromXML;
	@Use
	ElGamalProofDVRToXML elGamalProofDVRToXML;

	@Test
	@DisplayName("constructor and toXML works")
	void test() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_PROOF_DVR_XML,
				elGamalProofDVRToXML.apply(EL_GAMAL_PROOF_DVR));
	}

	@Test
	@DisplayName("getE works")
	void getETest() {
		assertEquals(CIPHERTEXT_E, EL_GAMAL_PROOF_DVR.e);
	}

	@Test
	@DisplayName("getEprime works")
	void getEprimeTest() {
		assertEquals(CIPHERTEXT_EPRIME, EL_GAMAL_PROOF_DVR.eprime);
	}

	@Test
	@DisplayName("fromXML works")
	void fromXMLTest() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_PROOF_DVR_XML,
				elGamalProofDVRToXML.apply((elGamalProofDVRFromXML
						.apply(new StringReader(EL_GAMAL_PROOF_DVR_XML)))));
	}

}
