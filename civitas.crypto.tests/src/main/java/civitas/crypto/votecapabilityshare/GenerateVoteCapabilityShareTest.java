package civitas.crypto.votecapabilityshare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.crypto.CryptoException;

class GenerateVoteCapabilityShareTest extends RandomAwareTestBase
		implements VoteCapabilityShareTestData {

	@InjectMocks
	GenerateVoteCapabilityShare generateVoteCapabilityShare;

	@Test
	@DisplayName("generates a vote capability share based on a random")
	void test() throws CryptoException {
		assertEquals(BIGINT_G.modPow(RANDOMS_0, BIGINT_P),
				generateVoteCapabilityShare.apply(EL_GAMAL_PARAMETERS).m);
	}
}
