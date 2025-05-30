/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.oneoflreencryption;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.proof1ofl.ElGamalProof1OfLC;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class ElGamal1OfLReencryption {
	@NonNull
	public final ElGamalCiphertext m;
	@NonNull
	public final ElGamalProof1OfLC proof;

}
