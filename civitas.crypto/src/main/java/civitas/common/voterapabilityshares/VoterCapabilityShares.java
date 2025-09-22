/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.voterapabilityshares;

import civitas.crypto.votecapabilityshare.VoteCapabilityShare;
import lombok.Data;

@Data
public class VoterCapabilityShares {
	public final VoteCapabilityShare[] capabilities;
	public final int voterBlock;
}
