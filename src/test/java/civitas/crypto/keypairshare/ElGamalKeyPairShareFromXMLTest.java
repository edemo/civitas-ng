package civitas.crypto.keypairshare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

public class ElGamalKeyPairShareFromXMLTest extends TestBase
		implements ElGamalKeyPairShareTestData {

	@Tested
	ElGamalKeyPairShareFromXML elGamalKeyPairShareFromXML;

	@Test
	@DisplayName("creates an ElGamalKeyPairShare fromXML")
	void test() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMAL_KEYPAIR_SHARE, elGamalKeyPairShareFromXML
				.apply(new StringReader(EL_GAMAL_KEYPAIR_SHARE_XML)));
	}

}
