/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.election;

import lombok.Data;

@Data
public class ElectionAbandonment {
	public final int tellerIndex;
	public final boolean isTabulationTeller;
	public final String reason;

}