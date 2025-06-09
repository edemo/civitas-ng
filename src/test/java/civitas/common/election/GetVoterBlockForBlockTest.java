package civitas.common.election;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class GetVoterBlockForBlockTest extends TestBase implements ElectionTestData {

	@Tested
	GetVoterBlockForBlock getVoterBlockForBlock;

	@Test
	@DisplayName("calculates the index of voter for the index of the vote\n"
			+ "simply by dividing the vote index with the vote length")
	void test() {
		assertEquals(1, getVoterBlockForBlock.apply(ELECTION_DETAILS, 4));
	}

}
