package civitas.common.votersubmission;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

	@Test
	@DisplayName("If the public key is null, then IllegalArgumentException is thrown")
	void test1() {
		assertThrows(IllegalArgumentException.class,
				() -> checkVoterSubmission.apply(BALLOTDESIGN, VOTER_SUBMISSION,
						ADDITIONALENV, CIPHERTEXT_LIST, null));
	}

	@Test
	@DisplayName("If the context is null, then IllegalArgumentException is thrown")
	void test1_1() {
		assertThrows(IllegalArgumentException.class,
				() -> checkVoterSubmission.apply(BALLOTDESIGN, VOTER_SUBMISSION, null,
						CIPHERTEXT_LIST, EL_GAMAL_PUBLIC_KEY_E));
	}

	@Test
	@DisplayName("If the submission is null, then IllegalArgumentException is thrown")
	void test2() {

		assertThrows(IllegalArgumentException.class,
				() -> checkVoterSubmission.apply(BALLOTDESIGN, null, ADDITIONALENV,
						CIPHERTEXT_LIST, EL_GAMAL_PUBLIC_KEY_E));
	}

	@Test
	@DisplayName("If a verifiable vote in the submission is null, then IllegalArgumentException is thrown")
	void test3() {

		assertThrows(IllegalArgumentException.class,
				() -> checkVoterSubmission.apply(BALLOTDESIGN,
						VOTER_SUBMISSION_WITH_NULL, ADDITIONALENV, CIPHERTEXT_LIST,
						EL_GAMAL_PUBLIC_KEY_E));
	}

	@Test
	@DisplayName("If the context in a verifiable vote is not correct, then IllegalArgumentException is thrown")
	void test4() {

		assertThrows(IllegalArgumentException.class,
				() -> checkVoterSubmission.apply(BALLOTDESIGN,
						VOTER_SUBMISSION_WITH_BAD_CONTEXT, ADDITIONALENV, CIPHERTEXT_LIST,
						EL_GAMAL_PUBLIC_KEY_E));
	}

	@Test
	@DisplayName("If the context in a verifiable vote is null, then IllegalArgumentException is thrown")
	void test4_1() {

		assertThrows(IllegalArgumentException.class,
				() -> checkVoterSubmission.apply(BALLOTDESIGN,
						VOTER_SUBMISSION_WITH_BAD_CONTEXT, ADDITIONALENV, CIPHERTEXT_LIST,
						EL_GAMAL_PUBLIC_KEY_E));
	}

	@Test
	@DisplayName("If a verifiable vote does not verify, then IllegalArgumentException is thrown")
	void test5() {

		assertThrows(IllegalArgumentException.class,
				() -> checkVoterSubmission.apply(BALLOTDESIGN,
						VOTER_SUBMISSION_WITH_BAD_PROOF, ADDITIONALENV, CIPHERTEXT_LIST,
						EL_GAMAL_PUBLIC_KEY_E));
	}

}
