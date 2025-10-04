/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.election;

import civitas.common.tallystatefinal.TallyStateFinal;
import lombok.Value;

@Value
public class ElectionEventFinalize implements ElectionEvent {
	public String kind = EVENT_KIND_FINALIZE;
	public ElectionID electionID;
	public int sequence;
	public TallyStateFinal tally;
	public String message;
}
