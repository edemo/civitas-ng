/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.oneoflreencryption;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.proof1ofl.ElGamalProof1OfL;
import lombok.NonNull;

public record ElGamal1OfLReencryption(@NonNull ElGamalCiphertextish m, @NonNull ElGamalProof1OfL proof) {}
