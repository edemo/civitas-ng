/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.verifiablevote;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.proofvote.ProofVote;
import lombok.NonNull;

public record VerifiableVote(
		@NonNull String context,
		@NonNull ElGamal1OfLReencryption encChoice,
		@NonNull ElGamalCiphertextish encCapability,
		@NonNull ProofVote proofVote) {}
