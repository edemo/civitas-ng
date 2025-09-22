package civitas.common.votersubmission;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateVoterSubmissionStub implements VoterSubmissionTestData {

	public static CreateVoterSubmission stub() {
		CreateVoterSubmission mock = mock(CreateVoterSubmission.class);
		when(mock.apply(VOTER_BLOCK, VERIFIABLE_VOTES)).thenReturn(VOTER_SUBMISSION);
		return mock;
	}
}
