package civitas.common.votersubmission;

import civitas.common.verifiablevote.VerifiableVote;

public class CreateVoterSubmission {

	public VoterSubmission apply(int voterBlock, VerifiableVote[] votes) {
		if (votes == null)
			throw new IllegalArgumentException("votes are null");
		return new VoterSubmission(voterBlock, votes.clone());
	}

}
