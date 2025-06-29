package civitas.common.mix.votemix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.Vote;
import civitas.common.mix.AddCommitmentToMix;
import lombok.NonNull;

@Controller
public class AddVoteAndCommitmentToVoteMix {
	@Autowired
	AddCommitmentToMix addCommitmentToMix;

	@Autowired
	AddVoteToVoteMix addVoteToVoteMix;

	public void apply(@NonNull VoteMix that, @NonNull Vote v,
			@NonNull byte[] commitment) throws ClassCastException

	{
		addVoteToVoteMix.apply(that, v);
		addCommitmentToMix.apply(that, commitment);
	}

}
