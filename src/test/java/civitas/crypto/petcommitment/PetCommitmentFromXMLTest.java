package civitas.crypto.petcommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.petdecommitment.PetCommitmentFromXML;
import civitas.util.Use;

public class PetCommitmentFromXMLTest extends TestBase
		implements PETCommitmentTestData {
	@Use
	PetCommitmentFromXML petCommitmentFromXML;

	@Test
	@DisplayName("fromXML works as expected")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(PET_COMMITMENT,
				petCommitmentFromXML.apply(new StringReader(PET_COMMITMENT_XML)));
	}

}
