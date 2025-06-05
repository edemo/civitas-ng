package civitas.common.ballotdesign;

import civitas.common.CommonConstants;

public class ConvertStringToChoice implements CommonConstants {

	public byte apply(String choice) {
		if (VOTE_CHOICE_I_BEATS_J_STRING.equals(choice))
			return VOTE_CHOICE_I_BEATS_J;
		if (VOTE_CHOICE_J_BEATS_I_STRING.equals(choice))
			return VOTE_CHOICE_J_BEATS_I;
		if (VOTE_CHOICE_NEITHER_BEAT_STRING.equals(choice))
			return VOTE_CHOICE_NEITHER_BEAT;
		return -1;
	}

}
