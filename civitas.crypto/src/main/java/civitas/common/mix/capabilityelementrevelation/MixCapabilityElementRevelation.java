/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.mix.capabilityelementrevelation;

import civitas.common.mix.elementrevelation.MixElementRevelation;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import lombok.Value;

@Value
public class MixCapabilityElementRevelation implements MixElementRevelation {
	int mapping;
	byte[] nonce;
	public ElGamalReencryptFactor reencryptFactor;
}
