package civitas.common.votersubmission;

import org.springframework.stereotype.Service;

import civitas.common.verifiablevote.VerifiableVote;

@Service
public class CreateVoterSubmission {

	public VoterSubmission apply(int voterBlock, VerifiableVote[] votes) {
		if (votes == null)
			throw new IllegalArgumentException("votes are null");
		return new VoterSubmission(voterBlock, votes.clone());
	}

}
