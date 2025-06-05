package civitas.common.votersubmission;

import civitas.common.verifiablevote.VerifiableVote;

public class CreateVoterSubmission {

	public VoterSubmission apply(int voterBlock, VerifiableVote[] votes) {
		VerifiableVote[] vs = null;
		if (votes != null) {
			vs = votes.clone();
		}
		return new VoterSubmission(voterBlock, vs);
	}

}
