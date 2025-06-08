package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class GetNthContextTest extends TestBase implements BallotDesignTestData {

	@Tested
	GetNthContext GetNthContext;

	@Test
	@DisplayName("gets the context for the nth element of the ballot")
	void test() {
		assertEquals(BARE_CONTEXT_1, GetNthContext.nthContext(BALLOTDESIGN, 1));
	}

	@Test
	@DisplayName("in case n > length of the matrix, null returned")
	void test1() {
		assertEquals(null, GetNthContext.nthContext(BALLOTDESIGN, 4));
	}

}
