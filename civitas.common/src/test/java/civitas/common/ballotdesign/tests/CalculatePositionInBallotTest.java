package civitas.common.ballotdesign.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.ballotdesign.CalculatePositionInBallot;
import civitas.common.tests.RandomAwareTestBase;

class CalculatePositionInBallotTest extends RandomAwareTestBase {

	@InjectMocks
	CalculatePositionInBallot calculatePositionInBallot;

	@Test
	@DisplayName("calculates the position of the ballot where\n"
			+ "candidate i and candidate j are compared in a vote with k candidates")
	void test() {
		assertEquals(2, calculatePositionInBallot.apply(2, 2, 3));
	}
}
