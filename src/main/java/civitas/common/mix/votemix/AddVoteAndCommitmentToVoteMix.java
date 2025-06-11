package civitas.common.mix.votemix;

import civitas.common.Vote;
import civitas.common.mix.AddCommitmentToMix;
import civitas.util.Use;
import lombok.NonNull;

public class AddVoteAndCommitmentToVoteMix {
	@Use
	AddCommitmentToMix addCommitmentToMix;

	@Use
	AddVoteToVoteMix addVoteToVoteMix;

	public void apply(@NonNull VoteMix that, @NonNull Vote v,
			@NonNull byte[] commitment) throws ClassCastException

	{
		addVoteToVoteMix.apply(that, v);
		addCommitmentToMix.apply(that, commitment);
	}

}
