/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.mix.votemix;

import civitas.common.Vote;
import civitas.common.mix.Mix;
import lombok.Data;
import lombok.NonNull;

@Data
public class VoteMix implements Mix {
	@NonNull
	public Integer number;
	@NonNull
	public byte[] mixNonceHash;
	@NonNull
	public byte[][] commitments;
	@NonNull
	public Vote[] votes;

	@Deprecated
	public int size() {
		return votes.length;
	}

}