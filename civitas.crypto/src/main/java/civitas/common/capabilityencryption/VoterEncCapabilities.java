/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.capabilityencryption;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import lombok.Value;

@Value
public class VoterEncCapabilities {
	public String name;
	public int voterBlock;
	public ElGamalCiphertext[] encCapabilities;
}
