package civitas.common.mix.capabilitymix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.common.mix.AddCommitmentToMix;

class AddCommitmentToMixTest extends TestBase implements CapabilityMixTestData {

	@InjectMocks
	AddCommitmentToMix addCommitmentToMix;

	@Test
	void test() {

		CapabilityMix mix = CAPABILITY_MIX_EMPTY_SUPPLIER.get();
		addCommitmentToMix.apply(mix, SOMESTRING.getBytes());
		assertEquals(CAPABILITY_MIX_COMMITMENT_ADDED_SUPPLIER.get(), mix);
	}

}
