package civitas.common.votersubmission;

import civitas.util.DI;

public class CreateVoterSubmissionStub {

	public static CreateVoterSubmission stub() {
		CreateVoterSubmission instance = new CreateVoterSubmission();
		DI.stubFill(instance);
		return instance;
	}
}
