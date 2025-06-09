package civitas.common.capabilitymix;

import java.util.Arrays;

import civitas.common.Mix;

public class AddCommitmentToMix {
	protected void apply(Mix that, byte[] commitment) {
		byte[][] commitments = that.getCommitments();
		byte[][] n = Arrays.copyOf(commitments, commitments.length + 1);
		n[commitments.length] = commitment.clone();
		that.setCommitments(n);
	}
}
