/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import civitas.util.Boilerplate;

/**
 * TallyState for a CondorcetBallotDesign.
 */
@Boilerplate
public class CondorcetTallyState extends TallyState {
	private final int[][] matrix;
	private final int size;

	public CondorcetTallyState(int size) {
		super();
		this.matrix = new int[size < 0 ? 0 : size][size < 0 ? 0 : size];
		this.size = size;
	}

	public void record(int i, int j) throws IndexOutOfBoundsException {
		if (matrix != null && i >= 0 && i < size && j >= 0 && j < size) {
			try {
				matrix[i][j]++;
				return;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			} catch (NullPointerException imposs) {
			}
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public TallyStateFinal finalTally() {
		return new CondorcetTallyStateFinal(matrix, size);
	}
}