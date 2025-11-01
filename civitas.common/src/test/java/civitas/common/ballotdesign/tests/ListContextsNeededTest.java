package civitas.common.ballotdesign.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.ballotdesign.ListContextsNeeded;
import civitas.common.tests.RandomAwareTestBase;

class ListContextsNeededTest extends RandomAwareTestBase implements BallotDesignTestData {

	@InjectMocks
	ListContextsNeeded listContextsNeeded;

	@DisplayName("generates a list of contexts needed for a ballot design")
	@Test
	void test() {
		assertEquals(CONTEXTS, listContextsNeeded.apply(BALLOTDESIGN, ADDITIONALENV));
	}
}
