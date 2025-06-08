package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class ListContextsNeededTest extends TestBase implements BallotDesignTestData {

	@Tested
	ListContextsNeeded listContextsNeeded;

	@DisplayName("generates a list of contexts needed for a ballot design")
	@Test
	void test() {
		assertEquals(CONTEXTS,
				listContextsNeeded.apply(BALLOTDESIGN, ADDITIONALENV));
	}

}
