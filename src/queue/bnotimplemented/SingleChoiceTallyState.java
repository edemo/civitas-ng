/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

/**
 * TallyState for a SingleChoiceballotDesign.
 */
public class SingleChoiceTallyState extends TallyState {
	private final int[] counts;
	public final String[] candidates;

	SingleChoiceTallyState(String[] candidates) {
		super();
		this.candidates = candidates;
		int size = candidates == null ? 0 : candidates.length;
		this.counts = new int[size < 0 ? 0 : size];
	}

	SingleChoiceTallyState(String[] candidates, int[] counts) {
		super();
		this.candidates = candidates;
		this.counts = counts;
	}

	void increment(int i) throws IndexOutOfBoundsException {
		if (counts != null && i >= 0 && i < counts.length) {
			try {
				counts[i]++;
				return;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		throw new IndexOutOfBoundsException();
	}

	public TallyStateFinal finalTally() {
		return new SingleChoiceTallyStateFinal(candidates, counts);
	}
}