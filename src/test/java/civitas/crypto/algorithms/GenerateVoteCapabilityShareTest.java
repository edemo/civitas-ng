package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.VoteCapabilityShareC;
import civitas.crypto.concrete.VoteCapabilityShareTestData;
import civitas.util.Tested;

public class GenerateVoteCapabilityShareTest extends ConcreteTestBase
		implements VoteCapabilityShareTestData {

	@Tested
	GenerateVoteCapabilityShare generateVoteCapabilityShare;

	@Test
	@DisplayName("generates a vote capability share based on a random")
	void test() {
		assertEquals(BIGINT_G.modPow(RANDOMS_0, BIGINT_P),
				((VoteCapabilityShareC) generateVoteCapabilityShare
						.apply(EL_GAMAL_PARAMETERS)).m);
	}
}
