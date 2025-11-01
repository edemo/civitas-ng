package civitas.common.mix.elementrevelation.tests;

import static org.mockito.Mockito.mock;

import civitas.common.mix.elementrevelation.MixElementRevelation;

public interface MixElementRevelationTestData {
	MixElementRevelation MIX_ELEMENT_REVELATION_MOCK = mock(MixElementRevelation.class);

	String VOTE_REVELATION_META = "mixRevelation:vote:voterBlock-4-context-condorcet1:2:2";
	String ROLL_REVELATION_META = "mixRevelation:elecRoll:voterBlock-4-context-condorcet1:2:2";
}
