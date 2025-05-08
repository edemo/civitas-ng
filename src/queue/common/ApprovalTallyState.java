/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

/**
 * TallyState for a ApprovalBallotDesign.
 */
public class ApprovalTallyState extends TallyState {
	private final int[] approvals;
	private final int[] disapprovals;
	public final String[] candidates;

	ApprovalTallyState(String[] candidates) {
		super();
		this.candidates = candidates;
		int size = 0;
		if (candidates != null)
			size = candidates.length;
		this.approvals = new int[size];
		this.disapprovals = new int[size];
	}

	void record(int i, boolean approve) throws IndexOutOfBoundsException {
		if (approvals != null && disapprovals != null && i >= 0 && i < approvals.length) {
			try {
				if (approve) {
					approvals[i]++;
				} else {
					disapprovals[i]++;
				}
				return;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		throw new IndexOutOfBoundsException();
	}

	public TallyStateFinal finalTally() {
		return new ApprovalTallyStateFinal(candidates, approvals, disapprovals);
	}
}