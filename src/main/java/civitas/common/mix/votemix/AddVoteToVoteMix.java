package civitas.common.mix.votemix;

import java.util.Arrays;

import org.springframework.stereotype.Controller;

import civitas.common.Vote;
import lombok.NonNull;

@Controller
public class AddVoteToVoteMix {
	public void apply(@NonNull VoteMix that, @NonNull Vote v) {
		Vote[] n = Arrays.copyOf(that.votes, that.votes.length + 1);
		n[that.votes.length] = v;
		that.votes = n;
	}

}
