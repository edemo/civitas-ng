package civitas.common.mix.capabilitymix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.proofvote.ProofVoteTestData;
import civitas.util.Tested;

class AddEncryptedCapabilityTest extends TestBase
		implements CapabilityMixTestData, ProofVoteTestData {

	@Tested
	AddEncryptedCapability addEncryptedCapability;

	@Test
	@DisplayName("Adds an encrypted capability to the mix")
	void test() {
		CapabilityMix mix = CAPABILITY_MIX_EMPTY_SUPPLIER.get();
		addEncryptedCapability.apply(mix, ENCRYPTED_VOTE_CAPABILITIES.get(0));
		assertEquals(CAPABILITY_MIX_CAPABILITY_ADDED, mix);
	}

}
