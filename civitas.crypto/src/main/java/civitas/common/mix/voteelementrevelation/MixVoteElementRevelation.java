/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.mix.voteelementrevelation;

import civitas.common.mix.elementrevelation.MixElementRevelation;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import lombok.Value;

@Value
public class MixVoteElementRevelation implements MixElementRevelation {

	public ElGamalReencryptFactor choiceFactor;
	public ElGamalReencryptFactor reencryptFactor;
	public int mapping;
	public byte[] nonce;
}
