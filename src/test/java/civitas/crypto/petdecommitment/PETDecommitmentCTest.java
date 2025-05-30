package civitas.crypto.petdecommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.parameters.ElGamalParametersCTestData;
import civitas.util.Use;

public class PETDecommitmentCTest extends ConcreteTestBase
		implements PETDecommitmentCTestData, ElGamalParametersCTestData {

	@Use
	PetDecommitmentFromXML petDecommitmentFromXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PET_DECOMMITMENT_XML, PET_DECOMMITMENT.toXML());
	}

	@Test
	@DisplayName("constructor arguments di, ei can be null")
	void test1() {
		assertEquals(PET_DECOMMITMENT_NULL_XML, new PETDecommitmentC(null, null,
				EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT).toXML());
	}

	@Test
	@DisplayName("if the proof is null, toXML throws a NullPointerException")
	void test2() {
		assertThrows(NullPointerException.class,
				() -> new PETDecommitmentC(PET_DECOMMITMENT_D, PET_DECOMMITMENT_E, null)
						.toXML());
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test3() throws IllegalArgumentException, IOException {
		assertEquals(PET_DECOMMITMENT_XML, petDecommitmentFromXML
				.apply(new StringReader(PET_DECOMMITMENT_XML)).toXML());
	}

	@Test
	@DisplayName("proof returns the proof")
	void test4() {
		assertEquals(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT,
				PET_DECOMMITMENT.proof());
	}

}
