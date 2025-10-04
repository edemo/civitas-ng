/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.mix.votemix;

import civitas.common.EncryptedVote;
import civitas.common.mix.VoterMix;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder(toBuilder = true)
public class VoteMix implements VoterMix {
	@NonNull public Integer number;

	public byte @NonNull [] mixNonceHash;

	public byte[][] commitments;

	@NonNull public EncryptedVote[] votes;

	@Deprecated
	public int size() {
		return votes.length;
	}
}
