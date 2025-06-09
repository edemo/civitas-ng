package civitas.common.capabilitymix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class GetReencryptedCapabilityMixTest extends TestBase
		implements CapabilityMixTestData {

	@Tested
	GetReencryptedCapabilityMix getReencryptedCapabilityMix;

	@Test
	void test() {
		assertEquals(REENCRYPTED_VOTE_CAPABILITIES.get(0),
				getReencryptedCapabilityMix.apply(CAPABILITY_MIX_CAPABILITY_ADDED, 0,
						ELGAMAL_REENCRYPT_FACTOR_EPRIME, EL_GAMAL_PUBLIC_KEY_EPRIME));
	}

}
