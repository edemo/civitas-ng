package civitas.common.electoralroll;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class GetMetaForElectoralRollCapabilitySharesTest extends TestBase
		implements ElectoralRollTestData {

	@Tested
	GetMetaForElectoralRollCapabilityShares getMetaForElectoralRollCapabilityShares;

	@Test
	void test() {
		assertEquals(META_FOR_ELECTORAL_ROLL_CAPABILITY_SHARES,
				getMetaForElectoralRollCapabilityShares.apply(1, 3));
	}

}
