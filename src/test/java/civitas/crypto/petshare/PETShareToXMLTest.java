package civitas.crypto.petshare;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.CryptoException;
import civitas.crypto.petdecommitment.PETDecommitmentTestData;
import civitas.util.Use;

public class PETShareToXMLTest extends TestBase
		implements PETDecommitmentTestData, PETShareTestData {

	@Use
	PETShareToXML pETShareCToXML;

	@Test
	@DisplayName("toXML works as expected")
	void test() throws CryptoException {
		assertEquals(PET_SHARE_XML, pETShareCToXML.apply(PET_SHARE));
	}

	@Test
	@DisplayName("toXML does nothing with a null PrintWriter")
	void test2() {
		assertDoesNotThrow(() -> pETShareCToXML.apply(PET_SHARE, null));
	}

}
