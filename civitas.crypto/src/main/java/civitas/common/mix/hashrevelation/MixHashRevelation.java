/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.mix.hashrevelation;

import lombok.Value;

@Value
public class MixHashRevelation {

	public int tellerIndex;

	public byte[] mixNonce;
}
