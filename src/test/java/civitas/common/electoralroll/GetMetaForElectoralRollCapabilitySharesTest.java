package civitas.common.electoralroll;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

class GetMetaForElectoralRollCapabilitySharesTest extends TestBase
		implements ElectoralRollTestData {

	@InjectMocks
	GetMetaForElectoralRollCapabilityShares getMetaForElectoralRollCapabilityShares;

	@Test
	void test() {
		assertEquals(META_FOR_ELECTORAL_ROLL_CAPABILITY_SHARES,
				getMetaForElectoralRollCapabilityShares.apply(1, 3));
	}

}
