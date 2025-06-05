/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.verifiablevote;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.proofvote.ProofVote;
import lombok.Data;
import lombok.NonNull;

/**
 * Similar to a <code>Vote</code>, but the capability is encrypted and signed,
 * and there is also a proof that the choice is in fact a valid choice. This is
 * part of what a voter submits; the tabulation process will strip off the
 * proof, and change the capability to be malleable (for the re-encryption
 * mixes).
 */
@Data
public class VerifiableVote {
	@NonNull
	public final String context;
	@NonNull
	public final ElGamal1OfLReencryption encChoice;
	@NonNull
	public final ElGamalCiphertext encCapability;
	@NonNull
	public final ProofVote proofVote;

}