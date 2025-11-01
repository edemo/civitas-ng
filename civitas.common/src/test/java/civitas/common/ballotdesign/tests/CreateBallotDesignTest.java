package civitas.common.ballotdesign.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.ballotdesign.CreateBallotDesign;
import civitas.common.tests.RandomAwareTestBase;

class CreateBallotDesignTest extends RandomAwareTestBase implements BallotDesignTestData {

	@InjectMocks
	CreateBallotDesign createBallotDesign;

	@DisplayName("creates a ballot design from the list of candidates")
	@Test
	void test() {
		assertEquals(BALLOTDESIGN, createBallotDesign.apply(CANDIDATES.toArray(new String[0])));
	}
}
