/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.mix.capabilitymix;

import civitas.common.mix.VoterMix;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import lombok.Data;
import lombok.NonNull;

@Data
public class CapabilityMix implements VoterMix {
	@NonNull public final Integer number;

	public final byte @NonNull [] mixNonceHash;

	@NonNull public byte[][] commitments;

	@NonNull public ElGamalCiphertextish[] capabilities;
}
