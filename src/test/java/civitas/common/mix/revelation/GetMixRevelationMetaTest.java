package civitas.common.mix.revelation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.common.election.ElectionTestData;
import civitas.common.mix.elementrevelation.MixElementRevelationTestData;
import civitas.util.Use;

class GetMixRevelationMetaTest extends TestBase
		implements ElectionTestData, MixElementRevelationTestData {

	@Use
	GetMixRevelationMeta getMixRevelationMeta;

	@Test
	@DisplayName("if isVoteMix, then gives 'mixRevelation:vote:<block description>:<teller index>'")
	void test() {
		assertEquals(VOTE_REVELATION_META,
				getMixRevelationMeta.apply(ELECTION_DETAILS, true, 1, 2));
	}

	@Test
	@DisplayName("if not VoteMix, then gives 'mixRevelation:elecRoll:<block description>:<teller index>'")
	void test1() {
		assertEquals(ROLL_REVELATION_META,
				getMixRevelationMeta.apply(ELECTION_DETAILS, false, 1, 2));
	}

}
