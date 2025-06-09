/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.capabilitymix;

import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import lombok.Data;

@Data
public class MixCapabilityElementRevelation implements MixElementRevelation {
	final int mapping;
	final byte[] nonce;
	public final ElGamalReencryptFactor reencryptFactor;

}