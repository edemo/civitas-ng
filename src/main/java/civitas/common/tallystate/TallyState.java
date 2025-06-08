/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.tallystate;

import lombok.Data;
import lombok.NonNull;

@Data
public class TallyState {
	@NonNull
	public final Integer size;
	@NonNull
	public final Integer[][] matrix;
	RecordBeat a;

}