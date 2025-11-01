package civitas.common.mix.capabilitymix.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.mix.AddCommitmentToMix;
import civitas.common.mix.capabilitymix.CapabilityMix;
import civitas.common.tests.RandomAwareTestBase;

class AddCommitmentToMixTest extends RandomAwareTestBase implements CapabilityMixTestData {

	@InjectMocks
	AddCommitmentToMix addCommitmentToMix;

	@Test
	void test() {

		CapabilityMix mix = CAPABILITY_MIX_EMPTY_SUPPLIER.get();
		addCommitmentToMix.apply(mix, SOMESTRING.getBytes());
		assertEquals(CAPABILITY_MIX_COMMITMENT_ADDED_SUPPLIER.get(), mix);
	}
}
