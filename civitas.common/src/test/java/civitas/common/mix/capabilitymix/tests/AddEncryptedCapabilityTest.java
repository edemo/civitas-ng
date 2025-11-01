package civitas.common.mix.capabilitymix.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.mix.capabilitymix.AddEncryptedCapability;
import civitas.common.mix.capabilitymix.CapabilityMix;
import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.proofvote.tests.ProofVoteTestData;

class AddEncryptedCapabilityTest extends RandomAwareTestBase implements CapabilityMixTestData, ProofVoteTestData {

	@InjectMocks
	AddEncryptedCapability addEncryptedCapability;

	@Test
	@DisplayName("Works when adding an encrypted capability to the empty mix")
	void test() {
		CapabilityMix mix = CAPABILITY_MIX_EMPTY_SUPPLIER.get();
		addEncryptedCapability.apply(mix, ENCRYPTED_SIGNED_VOTE_CAPABILITIES.getFirst());
		assertEquals(CAPABILITY_MIX_CAPABILITY_ADDED, mix);
	}

	@Test
	@DisplayName("Works when adding an encrypted capability to the nonempty mix")
	void test1() {
		CapabilityMix mix = CAPABILITY_MIX_CAPABILITY_ADDED;
		addEncryptedCapability.apply(mix, ENCRYPTED_SIGNED_VOTE_CAPABILITIES.get(1));
		assertEquals(CAPABILITY_MIX_CAPABILITIES_ADDED, mix);
	}
}
