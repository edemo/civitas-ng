package civitas.common.ballot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class CreateEmptyBallotTest extends TestBase implements BallotTestData {
	@Tested
	CreateEmptyBallot createEmptyBallot;

	@Test
	@DisplayName("creates an empty ballot for n candidates")
	void test() {
		assertEquals(BALLOT_EMPTY, createEmptyBallot.apply(3));
	}

	@Test
	@DisplayName("the number of candidates must be at least 2: one candidate and none of above")
	void test2() {
		assertThrows(IllegalArgumentException.class,
				() -> createEmptyBallot.apply(1));
	}

}
