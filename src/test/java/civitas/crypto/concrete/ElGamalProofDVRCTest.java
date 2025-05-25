package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.algorithms.ElGamalProofDVRCTestData;
import civitas.crypto.importing.ElGamalProofDVRFromXML;
import civitas.util.Use;

public class ElGamalProofDVRCTest extends ConcreteTestBase
		implements ElGamalProofDVRCTestData {
	@Use
	ElGamalProofDVRFromXML elGamalProofDVRFromXML;

	@Test
	@DisplayName("constructor and toXML works")
	void test() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_PROOF_DVR_XML, EL_GAMAL_PROOF_DVR.toXML());
	}

	@Test
	@DisplayName("getE works")
	void getETest() {
		assertEquals(EL_GAMAL_CIPHERTEXT_E, EL_GAMAL_PROOF_DVR.getE());
	}

	@Test
	@DisplayName("getEprime works")
	void getEprimeTest() {
		assertEquals(EL_GAMAL_CIPHERTEXT_A_ENCRYPTED_WITH_FACTOR_A,
				EL_GAMAL_PROOF_DVR.getEprime());
	}

	@Test
	@DisplayName("fromXML works")
	void fromXMLTest() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_PROOF_DVR_XML,
				((ElGamalProofDVRC) elGamalProofDVRFromXML
						.apply(new StringReader(EL_GAMAL_PROOF_DVR_XML))).toXML());
	}

}
