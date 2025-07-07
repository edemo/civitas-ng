/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

/**
 * TallyState for a MultiballotDesign.
 */
public class MultiTallyState extends TallyState {
	private final TallyState[] states;

	MultiTallyState(TallyState[] states) {
		super();
		this.states = states;
	}

	TallyState get(int i) {
		if (states != null && i >= 0 && i < states.length) {
			try {
				return states[i];
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		return null;
	}

	public TallyStateFinal finalTally() {
		TallyStateFinal[] finalStates = null;
		if (states != null) {
			int size = states.length;

			finalStates = new TallyStateFinal[size];
			for (int i = 0; i < size; i++) {
				try {
					TallyState s = states[i];
					if (s != null) {
						finalStates[i] = s.finalTally();
					}
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
		}
		return new MultiTallyStateFinal(finalStates);
	}
}