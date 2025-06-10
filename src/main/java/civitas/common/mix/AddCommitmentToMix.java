package civitas.common.mix;

import java.util.Arrays;

public class AddCommitmentToMix {
	public void apply(Mix that, byte[] commitment) {
		byte[][] commitments = that.getCommitments();
		byte[][] n = Arrays.copyOf(commitments, commitments.length + 1);
		n[commitments.length] = commitment.clone();
		that.setCommitments(n);
	}
}
