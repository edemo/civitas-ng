package civitas.crypto.keypairshare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

public class ElGamalKeyPairShareToXMLTest extends TestBase
		implements ElGamalKeyPairShareTestData {

	@Use
	ElGamalKeyPairShareToXML elGamalKeyPairShareToXML;

	@Test
	@DisplayName("creates an xml from the keypair")
	void test() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_KEYPAIR_SHARE_XML,
				elGamalKeyPairShareToXML.apply(EL_GAMAL_KEYPAIR_SHARE));
	}
}
