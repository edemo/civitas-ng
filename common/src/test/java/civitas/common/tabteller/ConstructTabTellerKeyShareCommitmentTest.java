package civitas.common.tabteller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.common.tabteller.keysharecommitment.TabTellerKeyShareCommitmentTestData;
import jakarta.xml.bind.JAXBException;

class ConstructTabTellerKeyShareCommitmentTest extends TestBase
		implements TabTellerKeyShareCommitmentTestData {

	@InjectMocks
	ConstructTabTellerKeyShareCommitment constructTabTellerKeyShareCommitment;

	@Test
	void test() throws JAXBException, UnsupportedEncodingException {
		assertEquals(TAB_TELLER_KEY_SHARE_COMMITMENT,
				constructTabTellerKeyShareCommitment.apply(TAB_TELLER_KEY_SHARE));
	}

}
