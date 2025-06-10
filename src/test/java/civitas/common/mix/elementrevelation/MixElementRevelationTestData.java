package civitas.common.mix.elementrevelation;

import static org.mockito.Mockito.mock;

public interface MixElementRevelationTestData {
	MixElementRevelation MIX_ELEMENT_REVELATION_MOCK = mock(
			MixElementRevelation.class);

	String VOTE_REVELATION_META = "mixRevelation:vote:voterBlock-0-context-condorcet0:2:2";
	String ROLL_REVELATION_META = "mixRevelation:elecRoll:voterBlock-0-context-condorcet0:2:2";

}
