package civitas.common.votersubmission;

import org.springframework.stereotype.Controller;

import civitas.common.verifiablevote.VerifiableVote;

@Controller
public class CreateVoterSubmission {

	public VoterSubmission apply(final int voterBlock, final VerifiableVote... votes) {
		if (votes.length == 0) {
			throw new IllegalArgumentException("votes are null");
		}
		return new VoterSubmission(voterBlock, votes.clone());
	}
}
