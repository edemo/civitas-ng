package civitas.common.ballotdesign;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.CommonConstants;

public class ConvertChoiceToStringStub implements CommonConstants {

	public static ConvertChoiceToString stub() {
		ConvertChoiceToString mock = mock(ConvertChoiceToString.class);
		when(mock.apply(anyInt())).thenReturn(VOTE_CHOICE_INVALID);
		when(mock.apply(VOTE_CHOICE_I_BEATS_J))
				.thenReturn(VOTE_CHOICE_I_BEATS_J_STRING);
		when(mock.apply(VOTE_CHOICE_J_BEATS_I))
				.thenReturn(VOTE_CHOICE_J_BEATS_I_STRING);
		when(mock.apply(VOTE_CHOICE_NEITHER_BEAT))
				.thenReturn(VOTE_CHOICE_NEITHER_BEAT_STRING);
		return mock;
	}
}
