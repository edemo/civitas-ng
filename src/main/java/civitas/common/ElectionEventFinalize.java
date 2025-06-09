/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import civitas.common.tallystatefinal.TallyStateFinal;
import lombok.Data;

@Data
public class ElectionEventFinalize implements ElectionEvent {
	public final String kind = EVENT_KIND_FINALIZE;
	public final ElectionID electionID;
	public final int sequence;
	public final TallyStateFinal tally;
	public final String message;

}