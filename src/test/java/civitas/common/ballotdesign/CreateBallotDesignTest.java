package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class CreateBallotDesignTest extends TestBase implements BallotDesignTestData {

	@Tested
	CreateBallotDesign createBallotDesign;

	@DisplayName("creates a ballot design from the list of candidates")
	@Test
	void test() {
		assertEquals(BALLOTDESIGN,
				createBallotDesign.apply(CANDIDATES.toArray(new String[0])));
	}

}
