package civitas.common.ballotdesign;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class CheckVoterSubmissionTest extends TestBase
		implements VoterSubmissionTestData {

	@Tested
	CheckVoterSubmission checkVoterSubmission;

	@Test
	@DisplayName("does not throw an exception if the submission is okay\n"
			+ "that means that for each of the votes recorded\n"
			+ "- the context should be in the form <context>'condorcet'<i>':'<j>\n"
			+ "- where i and j are the respective candidate indices")
	void test() {
		assertDoesNotThrow(
				() -> checkVoterSubmission.apply(BALLOTDESIGN, VOTER_SUBMISSION,
						ADDITIONALENV, CIPHERTEXT_LIST, EL_GAMAL_PUBLIC_KEY_E));
	}

}
