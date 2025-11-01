package civitas.common.ballotdesign.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.ballot.tests.BallotTestData;
import civitas.common.ballotdesign.CheckBallotAgainstBallotDesign;
import civitas.common.tests.RandomAwareTestBase;

class CheckBallotAgainstBallotDesignTest extends RandomAwareTestBase implements BallotDesignTestData, BallotTestData {

	@InjectMocks
	CheckBallotAgainstBallotDesign checkBallotAgainstBallotDesign;

	@Test
	@DisplayName("does not throw an exception for a correct ballot")
	void test0() {
		assertDoesNotThrow(() -> checkBallotAgainstBallotDesign.apply(BALLOTDESIGN, BALLOT));
	}

	@Test
	@DisplayName("checks whether the ballot is for the right number of candidates")
	void test() {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkBallotAgainstBallotDesign.apply(BALLOTDESIGN, BALLOT_2_CANDIDATES));
	}

	@Test
	@DisplayName("checks whether the ballot contains the right number of pairs")
	void test1() {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkBallotAgainstBallotDesign.apply(BALLOTDESIGN, BALLOT_SHORT_MATRIX));
	}

	@Test
	@DisplayName("checks whether all the pairs are valid")
	void test3() {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkBallotAgainstBallotDesign.apply(BALLOTDESIGN, BALLOT_ONE_RECORD));
	}
}
