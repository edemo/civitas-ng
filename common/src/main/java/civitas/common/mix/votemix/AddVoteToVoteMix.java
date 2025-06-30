package civitas.common.mix.votemix;

import java.util.Arrays;

import org.springframework.stereotype.Controller;

import civitas.common.Vote;
import jakarta.annotation.Nonnull;

@Controller
public class AddVoteToVoteMix {
	public void apply(@Nonnull VoteMix that, @Nonnull Vote v) {
		Vote[] n = Arrays.copyOf(that.votes, that.votes.length + 1);
		n[that.votes.length] = v;
		that.votes = n;
	}

}
