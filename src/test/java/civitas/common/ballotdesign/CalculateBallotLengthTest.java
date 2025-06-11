package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class CalculateBallotLengthTest extends TestBase {

	@Tested
	CalculateBallotLength calculateBallotLength;

	@Test
	@DisplayName("calculates the length of the ballot (the number of pairs in it)\n"
			+ "it is (k-1)*(k/2)")
	void test() {
		assertEquals(3, calculateBallotLength.apply(3));
	}

}
