/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.petshare;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.util.CivitasBigInteger;
import lombok.NonNull;

public record PETShare(
		@NonNull ElGamalCiphertextish ciphertext1,
		@NonNull ElGamalCiphertextish ciphertext2,
		@NonNull CivitasBigInteger exponent) {}
