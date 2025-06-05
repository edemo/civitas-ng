package civitas.common.ballotdesign;

import civitas.common.ballot.BallotTestData;
import civitas.common.verifiablevote.VerifiableVoteTestData;
import civitas.common.votersubmission.VoterSubmission;

public interface VoterSubmissionTestData
		extends VerifiableVoteTestData, BallotTestData, BallotDesignTestData {

	VoterSubmission VOTER_SUBMISSION = new VoterSubmission(3, VERIFIABLE_VOTES);

}
