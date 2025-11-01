package civitas.common.tabteller.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tabteller.ConstructTabTellerKeyShareCommitment;
import civitas.common.tabteller.keysharecommitment.tests.TabTellerKeyShareCommitmentTestData;
import civitas.common.tests.RandomAwareTestBase;
import jakarta.xml.bind.JAXBException;

class ConstructTabTellerKeyShareCommitmentTest extends RandomAwareTestBase
		implements TabTellerKeyShareCommitmentTestData {

	@InjectMocks
	ConstructTabTellerKeyShareCommitment constructTabTellerKeyShareCommitment;

	@Test
	void test() throws JAXBException {
		assertEquals(TAB_TELLER_KEY_SHARE_COMMITMENT, constructTabTellerKeyShareCommitment.apply(TAB_TELLER_KEY_SHARE));
	}
}
