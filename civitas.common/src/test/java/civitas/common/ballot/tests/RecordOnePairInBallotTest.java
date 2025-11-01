package civitas.common.ballot.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.VoteChoice;
import civitas.common.ballot.Ballot;
import civitas.common.ballot.RecordOnePairInBallot;
import civitas.common.tests.RandomAwareTestBase;

class RecordOnePairInBallotTest extends RandomAwareTestBase implements BallotTestData {

	@InjectMocks
	RecordOnePairInBallot recordOnePairInBallot;

	@Test
	@DisplayName("records whether candidate i beats candidate j")
	void test() {
		Ballot ballot = BALLOT_EMPTY.get();
		recordOnePairInBallot.apply(ballot, 0, 2, VoteChoice.I_BEATS_J);
		assertEquals(BALLOT_ONE_RECORD, ballot);
	}

	@Test
	@DisplayName("i must be >= 0, else IllegalArgumentException is thrown")
	void test2() {
		assertThrows(
				IllegalArgumentException.class,
				() -> recordOnePairInBallot.apply(BALLOT_EMPTY.get(), -1, 2, VoteChoice.I_BEATS_J));
	}

	@Test
	@DisplayName("j must be > i, else IllegalArgumentException is thrown")
	void test3() {
		assertThrows(
				IllegalArgumentException.class,
				() -> recordOnePairInBallot.apply(BALLOT_EMPTY.get(), 1, 1, VoteChoice.I_BEATS_J));
	}

	@Test
	@DisplayName("j must be < k, else IllegalArgumentException is thrown")
	void test4() {
		assertThrows(
				IllegalArgumentException.class,
				() -> recordOnePairInBallot.apply(BALLOT_EMPTY.get(), 0, 3, VoteChoice.I_BEATS_J));
	}

	@Test
	@DisplayName("the ballot must not be null else NullPointerException is thrown")
	void test5() {
		assertThrows(NullPointerException.class, () -> recordOnePairInBallot.apply(null, 0, 2, VoteChoice.I_BEATS_J));
	}
}
