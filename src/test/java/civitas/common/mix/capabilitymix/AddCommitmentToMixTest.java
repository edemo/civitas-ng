package civitas.common.mix.capabilitymix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.common.mix.AddCommitmentToMix;
import civitas.util.Tested;

class AddCommitmentToMixTest extends TestBase implements CapabilityMixTestData {

	@Tested
	AddCommitmentToMix addCommitmentToMix;

	@Test
	void test() {

		CapabilityMix mix = CAPABILITY_MIX_EMPTY_SUPPLIER.get();
		addCommitmentToMix.apply(mix, SOMESTRING.getBytes());
		assertEquals(CAPABILITY_MIX_COMMITMENT_ADDED, mix);
	}

}
