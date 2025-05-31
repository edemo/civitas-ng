package civitas.crypto.petdecommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.parameters.ElGamalParametersCTestData;
import civitas.util.Use;

public class PETDecommitmentCTest extends TestBase
		implements PETDecommitmentCTestData, ElGamalParametersCTestData {

	@Use
	PetDecommitmentFromXML petDecommitmentFromXML;
	@Use
	PETDecommitmentToXML petDecommitmentToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PET_DECOMMITMENT_XML,
				petDecommitmentToXML.apply(PET_DECOMMITMENT));
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test3() throws IllegalArgumentException, IOException {
		assertEquals(PET_DECOMMITMENT_XML, petDecommitmentToXML.apply(
				petDecommitmentFromXML.apply(new StringReader(PET_DECOMMITMENT_XML))));
	}

	@Test
	@DisplayName("proof returns the proof")
	void test4() {
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT,
				PET_DECOMMITMENT.proof);
	}

}
