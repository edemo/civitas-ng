/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.tallystate;

import civitas.common.tallystatefinal.TallyStateFinal;
import lombok.Data;
import lombok.NonNull;

/**
 * TallyState for a CondorcetBallotDesign.
 */
@Data
public class TallyState {
	@NonNull
	public final Integer size;
	@NonNull
	public final Integer[][] matrix;

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

	public TallyStateFinal finalTally() {
		return new TallyStateFinal(size, matrix);
	}
}