package civitas.common.election.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.election.GetContextForBlock;
import civitas.common.tests.RandomAwareTestBase;

class GetContextForBlockTest extends RandomAwareTestBase implements ElectionDetailsTestData {

	@InjectMocks
	GetContextForBlock getContextForBlock;

	@Test
	@DisplayName("gets the context index for a vote index in a block\n" + "this is simply vote index mod vote length")
	void test() {
		assertEquals(2, getContextForBlock.apply(ELECTION_DETAILS, 5));
	}
}
