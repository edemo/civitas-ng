package civitas.common.ballot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class RecordOnePairInBallotTest extends TestBase implements BallotTestData {

	@Tested
	RecordOnePairInBallot recordOnePairInBallot;

	@Test
	@DisplayName("records whether candidate i beats candidate j")
	void test() {
		recordOnePairInBallot.apply(BALLOT_EMPTY, 0, 2, VOTE_CHOICE_I_BEATS_J);
		assertEquals(BALLOT_ONE_RECORD, BALLOT_EMPTY);
	}

	@Test
	@DisplayName("i must be >= 0, else IllegalArgumentException is thrown")
	void test2() {
		assertThrows(IllegalArgumentException.class, () -> recordOnePairInBallot
				.apply(BALLOT_EMPTY, -1, 2, VOTE_CHOICE_I_BEATS_J));
	}

	@Test
	@DisplayName("j must be > i, else IllegalArgumentException is thrown")
	void test3() {
		assertThrows(IllegalArgumentException.class, () -> recordOnePairInBallot
				.apply(BALLOT_EMPTY, 1, 1, VOTE_CHOICE_I_BEATS_J));
	}

	@Test
	@DisplayName("j must be < k, else IllegalArgumentException is thrown")
	void test4() {
		assertThrows(IllegalArgumentException.class, () -> recordOnePairInBallot
				.apply(BALLOT_EMPTY, 0, 3, VOTE_CHOICE_I_BEATS_J));
	}

	@Test
	@DisplayName("the ballot must not be null else NullPointerException is thrown")
	void test5() {
		assertThrows(NullPointerException.class,
				() -> recordOnePairInBallot.apply(null, 0, 2, VOTE_CHOICE_I_BEATS_J));
	}

}
