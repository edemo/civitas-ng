package civitas.common.tabteller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import civitas.common.TestBase;
import civitas.common.tabteller.keysharecommitment.TabTellerKeyShareCommitmentTestData;
import civitas.util.Tested;

class ConstructTabTellerKeyShareCommitmentTest extends TestBase
		implements TabTellerKeyShareCommitmentTestData {

	@Tested
	ConstructTabTellerKeyShareCommitment constructTabTellerKeyShareCommitment;

	@Test
	void test() throws JsonProcessingException {
		assertEquals(TAB_TELLER_KEY_SHARE_COMMITMENT,
				constructTabTellerKeyShareCommitment.apply(TAB_TELLER_KEY_SHARE));
	}

}
