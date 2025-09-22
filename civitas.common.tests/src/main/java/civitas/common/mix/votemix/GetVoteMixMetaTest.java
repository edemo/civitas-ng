package civitas.common.mix.votemix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.common.election.ElectionDetailsTestData;

class GetVoteMixMetaTest extends RandomAwareTestBase
		implements ElectionDetailsTestData {

	@InjectMocks
	GetVoteMixMeta getVoteMixMeta;

	@Test
	@DisplayName("for a right mix the meta is in the form 'voteMix:<voter block name>:<mixnumber>R'")
	void test() {
		assertEquals("voteMix:voterBlock-4-context-condorcet1:2:5R",
				getVoteMixMeta.apply(ELECTION_DETAILS, 14, 5, true));
	}

	@Test
	@DisplayName("for a right mix the meta is in the form 'voteMix:<voter block name>:<mixnumber>L'")
	void test1() {
		assertEquals("voteMix:voterBlock-4-context-condorcet1:2:5L",
				getVoteMixMeta.apply(ELECTION_DETAILS, 14, 5, false));
	}

	@Test
	@DisplayName("if ElectionDetails is null, a NullPointerException is thrown")
	void test2() {
		assertThrows(NullPointerException.class,
				() -> getVoteMixMeta.apply(null, 14, 5, false));
	}

}
