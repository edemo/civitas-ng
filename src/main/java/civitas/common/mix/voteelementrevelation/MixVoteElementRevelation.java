/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.mix.voteelementrevelation;

import civitas.common.mix.elementrevelation.MixElementRevelation;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import lombok.Data;

@Data
public class MixVoteElementRevelation implements MixElementRevelation {

	public final ElGamalReencryptFactor choiceFactor;
	public final ElGamalReencryptFactor reencryptFactor;
	public final int mapping;
	public final byte[] nonce;

}