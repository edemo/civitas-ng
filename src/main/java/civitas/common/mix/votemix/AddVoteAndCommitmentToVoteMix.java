package civitas.common.mix.votemix;

import civitas.common.Vote;
import civitas.common.mix.AddCommitmentToMix;
import civitas.util.Use;

public class AddVoteAndCommitmentToVoteMix {
	@Use
	AddCommitmentToMix addCommitmentToMix;

	@Use
	AddVoteToVoteMix addVoteToVoteMix;

	public void add(VoteMix that, Object v, byte[] commitment)
			throws ClassCastException

	{
		addVoteToVoteMix.apply(that, (Vote) v);
		addCommitmentToMix.apply(that, commitment);
	}

}
