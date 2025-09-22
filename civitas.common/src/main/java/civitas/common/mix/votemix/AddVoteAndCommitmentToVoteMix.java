package civitas.common.mix.votemix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.EncryptedVote;
import civitas.common.mix.AddCommitmentToMix;
import jakarta.annotation.Nonnull;

@Controller
public class AddVoteAndCommitmentToVoteMix {
	@Autowired
	AddCommitmentToMix addCommitmentToMix;

	@Autowired
	AddVoteToVoteMix addVoteToVoteMix;

	public void apply(@Nonnull final VoteMix that, @Nonnull final EncryptedVote v, @Nonnull final byte[] commitment) {
		if (null == that || null == v || null == commitment) {
			throw new NullPointerException();
		}
		addVoteToVoteMix.apply(that, v);
		addCommitmentToMix.apply(that, commitment);
	}
}
