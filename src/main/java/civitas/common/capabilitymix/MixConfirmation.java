/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.capabilitymix;

import civitas.common.ElectionID;
import lombok.Data;

@Data
public class MixConfirmation {

	public final int speakerIndex;
	public final int tellerIndex;
	public final boolean isVoteMix;
	public final ElectionID electionID;

}