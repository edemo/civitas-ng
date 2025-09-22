package civitas.common.votersubmission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;

class CreateVoterSubmissionTest extends RandomAwareTestBase implements VoterSubmissionTestData {

	@InjectMocks
	CreateVoterSubmission createVoterSubmission;

	@Test
	@DisplayName("creates a voter submission")
	void test() {
		assertEquals(VOTER_SUBMISSION, createVoterSubmission.apply(VOTER_BLOCK, VERIFIABLE_VOTES));
	}

	@Test
	@DisplayName("if the votes are null, an IllegalArgumentException is thrown")
	void test1() {
		assertThrows(IllegalArgumentException.class, () -> createVoterSubmission.apply(VOTER_BLOCK));
	}
}
