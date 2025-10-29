package civitas.common.mix.revelation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.election.tests.ElectionDetailsTestData;
import civitas.common.mix.elementrevelation.tests.MixElementRevelationTestData;
import civitas.common.mix.revelation.GetMixRevelationMeta;
import civitas.common.tests.RandomAwareTestBase;

class GetMixRevelationMetaTest extends RandomAwareTestBase
		implements ElectionDetailsTestData, MixElementRevelationTestData {

	@InjectMocks
	GetMixRevelationMeta getMixRevelationMeta;

	@Test
	@DisplayName("if isVoteMix, then gives 'mixRevelation:vote:<block description>:<teller index>'")
	void test() {
		assertEquals(VOTE_REVELATION_META, getMixRevelationMeta.apply(ELECTION_DETAILS, true, 14, 2));
	}

	@Test
	@DisplayName("if not VoteMix, then gives 'mixRevelation:elecRoll:<block description>:<teller index>'")
	void test1() {
		assertEquals(ROLL_REVELATION_META, getMixRevelationMeta.apply(ELECTION_DETAILS, false, 14, 2));
	}

	@Test
	@DisplayName("if the election details is null, a NullPointerException is thrown")
	void test2() {
		assertThrows(NullPointerException.class, () -> getMixRevelationMeta.apply(null, false, 1, 2));
	}
}
