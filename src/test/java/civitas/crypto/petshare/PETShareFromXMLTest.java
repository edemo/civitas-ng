package civitas.crypto.petshare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.petdecommitment.PETDecommitmentTestData;
import civitas.util.Use;

public class PETShareFromXMLTest extends TestBase
		implements PETDecommitmentTestData, PETShareTestData {

	@Use
	PetShareFromXML petShareFromXML;

	@Test
	@DisplayName("fromXML works as expected")
	void test3() throws IOException {
		assertEquals(PET_SHARE,
				petShareFromXML.apply(new StringReader(PET_SHARE_XML)));
	}

}
