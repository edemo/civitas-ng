/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.mix.elementrevelation;

public interface MixElementRevelation {
//	protected final int mapping;
//	protected final byte[] nonce;
	int getMapping();

	byte[] getNonce();

//	boolean verify(ElGamalPublicKey key, int fromIndex, int toIndex, Mix fromMix,
//			Mix toMix);

}