package civitas.common.mix.voteelementrevelation.tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.mix.voteelementrevelation.VerifyMixVoteElementRevelation;

public class VerifyMixVoteElementRevelationStub implements MixVoteElementRevelationTestData {
	public static VerifyMixVoteElementRevelation stub() {
		VerifyMixVoteElementRevelation mock = mock(VerifyMixVoteElementRevelation.class);
		when(mock.apply(eq(VOTE_ELEMENT_REVELATION_MOCK), any(), anyInt(), anyInt(), any(), any()))
				.thenReturn(true);
		return mock;
	}
}
