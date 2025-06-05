package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.CommonConstants;
import civitas.common.TestBase;
import civitas.crypto.BasicValuesTestData;
import civitas.util.Tested;

class ConvertStringToChoiceTest extends TestBase
		implements CommonConstants, BasicValuesTestData {

	@Tested
	ConvertStringToChoice convertStringToChoice;

	@Test
	@DisplayName("converts the string describing the choice in the xml to the number representing it")
	void test() {
		assertEquals(VOTE_CHOICE_NEITHER_BEAT,
				convertStringToChoice.apply(VOTE_CHOICE_NEITHER_BEAT_STRING));
	}

	@Test
	@DisplayName("if the string does not represent a choice, -1 is returned")
	void test1() {
		assertEquals(-1, convertStringToChoice.apply(SOMESTRING));
	}

}
