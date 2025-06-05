package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.common.ballot.BallotTestData;
import civitas.util.Tested;

class CheckBallotAgainstBallotDesignTest extends TestBase
		implements BallotDesignTestData, BallotTestData {

	@Tested
	CheckBallotAgainstBallotDesign checkBallotAgainstBallotDesign;

	@Test
	@DisplayName("does not throw an exception for a correct ballot")
	void test0() {
		assertDoesNotThrow(
				() -> checkBallotAgainstBallotDesign.apply(BALLOTDESIGN, BALLOT));
	}

	@Test
	@DisplayName("checks whether the ballot is for the right number of candidates")
	void test() {
		assertThrows(IllegalArgumentException.class,
				() -> checkBallotAgainstBallotDesign.apply(BALLOTDESIGN,
						BALLOT_2_LENGTH));
	}

	@Test
	@DisplayName("checks whether the ballot contains the right number of pairs")
	void test1() {
		assertThrows(IllegalArgumentException.class,
				() -> checkBallotAgainstBallotDesign.apply(BALLOTDESIGN,
						BALLOT_SHORT_MATRIX));
	}

	@Test
	@DisplayName("checks whether all the pairs are valid")
	void test3() {
		assertThrows(IllegalArgumentException.class,
				() -> checkBallotAgainstBallotDesign.apply(BALLOTDESIGN,
						BALLOT_ONE_RECORD));
	}

}
