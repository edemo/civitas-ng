/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.election;

import civitas.common.tallystatefinal.TallyStateFinal;

public record ElectionEventFinalize(ElectionID electionID, int sequence, TallyStateFinal tally, String message)
		implements ElectionEvent {

	@Override
	public String kind() {
		return EVENT_KIND_FINALIZE;
	}
}
