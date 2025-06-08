package civitas.common.votersubmission;

import civitas.common.ballot.BallotTestData;
import civitas.common.ballotdesign.BallotDesignTestData;
import civitas.common.verifiablevote.VerifiableVote;
import civitas.common.verifiablevote.VerifiableVoteTestData;

public interface VoterSubmissionTestData

		extends VerifiableVoteTestData, BallotTestData, BallotDesignTestData {

	VoterSubmission VOTER_SUBMISSION = new VoterSubmission(VOTER_BLOCK,
			VERIFIABLE_VOTES);

	VoterSubmission VOTER_SUBMISSION_WITH_NULL = new VoterSubmission(VOTER_BLOCK,
			new VerifiableVote[] { null });
	VoterSubmission VOTER_SUBMISSION_WITH_BAD_CONTEXT = new VoterSubmission(
			VOTER_BLOCK, new VerifiableVote[] { VERIFIABLE_VOTES[1] });

	VoterSubmission VOTER_SUBMISSION_WITH_BAD_PROOF = new VoterSubmission(
			VOTER_BLOCK,
			new VerifiableVote[] {
					VERIFIABLE_VOTES[0],
					new VerifiableVote(VERIFIABLE_VOTES[1].context,
							VERIFIABLE_VOTES[1].encChoice, VERIFIABLE_VOTES[1].encCapability,
							PROOF_VOTE_MAP.get(0)),
					VERIFIABLE_VOTES[2] });

}
