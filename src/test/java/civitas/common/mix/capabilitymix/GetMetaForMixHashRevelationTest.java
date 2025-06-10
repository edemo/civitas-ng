package civitas.common.mix.capabilitymix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.common.mix.hashrevelation.GetMetaForMixHashRevelation;
import civitas.util.Tested;

class GetMetaForMixHashRevelationTest extends TestBase
		implements CapabilityMixTestData {

	@Tested
	GetMetaForMixHashRevelation getMetaForMixHashRevelation;

	@Test
	@DisplayName("the meta is in the format 'mixHashRevelation:vote:<blockname>:<tellerIndex>' for a vote")
	void test() {
		assertEquals(MIX_HASH_REVELATION_VOTE,
				getMetaForMixHashRevelation.apply(ELECTION_DETAILS, true, 14, 2));
	}

	@Test
	@DisplayName("the meta is in the format 'mixHashRevelation:elecRoll:<blockname>:<tellerIndex>' for a roll")
	void test1() {
		assertEquals(MIX_HASH_REVELATION_ROLL,
				getMetaForMixHashRevelation.apply(ELECTION_DETAILS, false, 14, 2));
	}

}
