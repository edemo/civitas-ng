package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class CalculatePositionInBallotTest extends TestBase {

	@Tested
	CalculatePositionInBallot calculatePositionInBallot;

	@Test
	@DisplayName("calculates the position of the ballot where\n"
			+ "candidate i and candidate j are compared in a vote with k candidates")
	void test() {
		assertEquals(2, calculatePositionInBallot.apply(2, 2, 3));
	}

}
