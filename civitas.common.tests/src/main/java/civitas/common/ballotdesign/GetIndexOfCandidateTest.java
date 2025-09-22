package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.util.BasicValuesTestData;

class GetIndexOfCandidateTest extends RandomAwareTestBase implements BallotDesignTestData, BasicValuesTestData {

	@InjectMocks
	GetIndexOfCandidate getIndexOfCandidate;

	@Test
	@DisplayName("gets index of candidate")
	void test() {
		assertEquals(1, getIndexOfCandidate.apply(BALLOTDESIGN, CANDIDATE));
	}

	@Test
	@DisplayName("finds the candidate even when case does not match")
	void test1() {
		assertEquals(1, getIndexOfCandidate.apply(BALLOTDESIGN, CANDIDATE.toLowerCase()));
	}

	@Test
	@DisplayName("if there is no such candidate -1 is returned")
	void test2() {
		assertEquals(-1, getIndexOfCandidate.apply(BALLOTDESIGN, SOMESTRING));
	}
}
