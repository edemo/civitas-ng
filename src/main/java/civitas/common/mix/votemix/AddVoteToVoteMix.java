package civitas.common.mix.votemix;

import civitas.common.Vote;

public class AddVoteToVoteMix {
	public void apply(VoteMix that, Vote v) {
		try {
			Vote[] n = new Vote[that.votes.length + 1];
			for (int i = 0; i < that.votes.length; i++) {
				try {
					n[i] = that.votes[i];
				} catch (

				NullPointerException ignore) {
				} catch (ArrayIndexOutOfBoundsException ignore) {
				}
			}
			n[that.votes.length] = v;
			that.votes = n;
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
	}

}
