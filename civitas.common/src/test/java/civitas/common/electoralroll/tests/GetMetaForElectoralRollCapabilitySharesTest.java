package civitas.common.electoralroll.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.electoralroll.GetMetaForElectoralRollCapabilityShares;
import civitas.common.tests.RandomAwareTestBase;

class GetMetaForElectoralRollCapabilitySharesTest extends RandomAwareTestBase
		implements ElectoralRollCapabilitySharesTestData {

	@InjectMocks
	GetMetaForElectoralRollCapabilityShares getMetaForElectoralRollCapabilityShares;

	@Test
	void test() {
		assertEquals(META_FOR_ELECTORAL_ROLL_CAPABILITY_SHARES, getMetaForElectoralRollCapabilityShares.apply(1, 3));
	}
}
