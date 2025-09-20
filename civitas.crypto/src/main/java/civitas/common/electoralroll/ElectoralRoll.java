/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.electoralroll;

import civitas.common.VoterDetails;
import lombok.Data;

@Data
public class ElectoralRoll {
	public static final String META = "electoralRoll";

	public final VoterDetails[] roll;
}
