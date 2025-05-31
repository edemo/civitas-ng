package civitas.crypto.petcommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.petdecommitment.PetCommitmentFromXML;
import civitas.util.Use;

public class PETCommitmentCTest extends TestBase
		implements PETCommitmentCTestData {
	@Use
	PetCommitmentFromXML petCommitmentFromXML;
	@Use
	PETCommitmentToXML pETCommitmentToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PET_C_XML, pETCommitmentToXML.apply(PET_COMMITMENT));
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(PET_C_XML, pETCommitmentToXML
				.apply(petCommitmentFromXML.apply(new StringReader(PET_C_XML))));
	}

}
