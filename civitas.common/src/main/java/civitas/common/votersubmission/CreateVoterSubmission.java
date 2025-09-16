package civitas.common.votersubmission;

import org.springframework.stereotype.Controller;

import civitas.common.verifiablevote.VerifiableVote;

@Controller
public class CreateVoterSubmission {

	public VoterSubmission apply(int voterBlock, VerifiableVote[] votes) {
		if (votes == null) {
			throw new IllegalArgumentException("votes are null");
		}
		return new VoterSubmission(voterBlock, votes.clone());
	}

}
