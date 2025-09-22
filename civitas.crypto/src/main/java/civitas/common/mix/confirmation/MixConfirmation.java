/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.mix.confirmation;

import civitas.common.election.ElectionID;

public record MixConfirmation(int speakerIndex, int tellerIndex,
		boolean isVoteMix, ElectionID electionID) {
}
