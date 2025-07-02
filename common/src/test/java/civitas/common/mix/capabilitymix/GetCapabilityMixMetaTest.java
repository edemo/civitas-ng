package civitas.common.mix.capabilitymix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.common.election.ElectionDetailsTestData;
import civitas.common.mix.hashrevelation.MixHashRevelationTestData;

class GetCapabilityMixMetaTest extends TestBase
		implements MixHashRevelationTestData, ElectionDetailsTestData {

	@InjectMocks
	GetCapabilityMixMeta getCapabilityMixMeta;

	@Test
	@DisplayName("gets the meta for the block, mix number, and mix type")
	void test() {
		assertEquals("capabilityMix:" + BLOCKNAME_14 + ":5L",
				getCapabilityMixMeta.apply(ELECTION_DETAILS, 14, 5, false));
	}

	@Test
	@DisplayName("A right mix name ends with R, a left mix name ends with L")
	void test_1() {
		assertEquals("capabilityMix:" + BLOCKNAME_14 + ":5R",
				getCapabilityMixMeta.apply(ELECTION_DETAILS, 14, 5, true));
	}

	@Test
	@DisplayName("the election cannot be null, or IllegalArgumentException is thrown")
	void test1() {
		assertThrows(IllegalArgumentException.class,
				() -> getCapabilityMixMeta.apply(null, 14, 5, false));
	}

}
