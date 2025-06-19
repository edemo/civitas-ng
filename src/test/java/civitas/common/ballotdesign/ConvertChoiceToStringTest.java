package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.CommonConstants;
import civitas.common.TestBase;

class ConvertChoiceToStringTest extends TestBase implements CommonConstants {

	@InjectMocks
	ConvertChoiceToString convertChoiceToString;

	@Test
	@DisplayName("VOTE_CHOICE_I_BEATS_J results in VOTE_CHOICE_I_BEATS_J_STRING")
	void test() {
		assertEquals(VOTE_CHOICE_I_BEATS_J_STRING,
				convertChoiceToString.apply(VOTE_CHOICE_I_BEATS_J));
	}

	@Test
	@DisplayName("VOTE_CHOICE_J_BEATS_I results in VOTE_CHOICE_J_BEATS_I_STRING")
	void test1() {
		assertEquals(VOTE_CHOICE_J_BEATS_I_STRING,
				convertChoiceToString.apply(VOTE_CHOICE_J_BEATS_I));
	}

	@Test
	@DisplayName("VOTE_CHOICE_NEITHER_BEAT results in VOTE_CHOICE_NEITHER_BEAT_STRING")
	void test2() {
		assertEquals(VOTE_CHOICE_NEITHER_BEAT_STRING,
				convertChoiceToString.apply(VOTE_CHOICE_NEITHER_BEAT));
	}

	@Test
	@DisplayName("invalid value results in VOTE_CHOICE_INVALID")
	void test3() {
		assertEquals(VOTE_CHOICE_INVALID, convertChoiceToString.apply(5));
	}

}
