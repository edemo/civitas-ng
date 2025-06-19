package civitas.common.ballotdesign;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.CommonConstants;

class ConvertStringToChoiceStub implements CommonConstants {
	public static ConvertStringToChoice stub() {
		ConvertStringToChoice mock = mock(ConvertStringToChoice.class);
		when(mock.apply(any())).thenReturn((byte) -1);
		when(mock.apply(VOTE_CHOICE_I_BEATS_J_STRING))
				.thenReturn((byte) VOTE_CHOICE_I_BEATS_J);
		when(mock.apply(VOTE_CHOICE_J_BEATS_I_STRING))
				.thenReturn((byte) VOTE_CHOICE_J_BEATS_I);
		when(mock.apply(VOTE_CHOICE_NEITHER_BEAT_STRING))
				.thenReturn((byte) VOTE_CHOICE_NEITHER_BEAT);
		return mock;
	}

}
