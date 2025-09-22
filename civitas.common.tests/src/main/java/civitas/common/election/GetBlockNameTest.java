package civitas.common.election;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;

class GetBlockNameTest extends RandomAwareTestBase implements ElectionDetailsTestData {

	@InjectMocks
	GetBlockName getBlockName;

	@Test
	void test() {
		assertEquals("voterBlock-4-context-" + BARE_CONTEXT_2, getBlockName.apply(ELECTION_DETAILS, 4 * 3 + 2));
	}
}
