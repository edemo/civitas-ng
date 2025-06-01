package civitas.crypto.petcommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

public class PetCommitmentToXMLTest extends TestBase
		implements PETCommitmentTestData {
	@Use
	PETCommitmentToXML pETCommitmentToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PET_COMMITMENT_XML, pETCommitmentToXML.apply(PET_COMMITMENT));
	}

}
