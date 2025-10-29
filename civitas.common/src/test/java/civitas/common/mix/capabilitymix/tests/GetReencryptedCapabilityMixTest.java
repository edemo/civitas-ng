package civitas.common.mix.capabilitymix.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.mix.capabilitymix.GetReencryptedCapabilityMix;
import civitas.common.tests.RandomAwareTestBase;

class GetReencryptedCapabilityMixTest extends RandomAwareTestBase implements CapabilityMixTestData {

	@InjectMocks
	GetReencryptedCapabilityMix getReencryptedCapabilityMix;

	@Test
	void test() {
		assertEquals(
				REENCRYPTED_VOTE_CAPABILITIES.getFirst(),
				getReencryptedCapabilityMix.apply(
						CAPABILITY_MIX_CAPABILITY_ADDED,
						0,
						ELGAMAL_REENCRYPT_FACTOR_EPRIME,
						EL_GAMAL_PUBLIC_KEY_EPRIME));
	}
}
