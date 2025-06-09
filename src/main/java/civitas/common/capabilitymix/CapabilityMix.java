/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.capabilitymix;

import civitas.common.Mix;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import lombok.Data;
import lombok.NonNull;

@Data
public class CapabilityMix implements Mix {
	@NonNull
	public final Integer number;
	@NonNull
	public final byte[] mixNonceHash;
	@NonNull
	public byte[][] commitments;
	@NonNull
	public ElGamalCiphertext[] capabilities;

}