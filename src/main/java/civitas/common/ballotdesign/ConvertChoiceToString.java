package civitas.common.ballotdesign;

import org.springframework.stereotype.Service;

import civitas.common.CommonConstants;

@Service
public class ConvertChoiceToString implements CommonConstants {

	public String apply(int choice) {
		switch (choice) {
		case VOTE_CHOICE_I_BEATS_J:
			return VOTE_CHOICE_I_BEATS_J_STRING;
		case VOTE_CHOICE_J_BEATS_I:
			return VOTE_CHOICE_J_BEATS_I_STRING;
		case VOTE_CHOICE_NEITHER_BEAT:
			return VOTE_CHOICE_NEITHER_BEAT_STRING;
		}
		return VOTE_CHOICE_INVALID;
	}

}
