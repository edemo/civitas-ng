package civitas.common.election;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class GetContextForBlockTest extends TestBase implements ElectionTestData {

	@Tested
	GetContextForBlock getContextForBlock;

	@Test
	@DisplayName("gets the context index for a vote index in a block\n"
			+ "this is simply vote index mod vote length")
	void test() {
		assertEquals(2, getContextForBlock.apply(ELECTION_DETAILS, 5));
	}

}
