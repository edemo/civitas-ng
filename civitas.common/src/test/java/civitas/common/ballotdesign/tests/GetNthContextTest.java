package civitas.common.ballotdesign.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.ballotdesign.GetNthContext;
import civitas.common.tests.RandomAwareTestBase;

class GetNthContextTest extends RandomAwareTestBase implements BallotDesignTestData {

	@InjectMocks
	GetNthContext getNthContext;

	@Test
	@DisplayName("gets the context for the nth element of the ballot")
	void test() {
		assertEquals(BARE_CONTEXT_2, getNthContext.apply(BALLOTDESIGN, 2));
	}

	@Test
	@DisplayName("in case n > length of the matrix, null returned")
	void test1() {
		assertEquals(null, getNthContext.apply(BALLOTDESIGN, 4));
	}
}
