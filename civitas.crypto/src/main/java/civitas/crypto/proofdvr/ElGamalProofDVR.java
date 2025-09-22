/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.proofdvr;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.util.CivitasBigInteger;
import lombok.NonNull;

public record ElGamalProofDVR(@NonNull ElGamalCiphertextish e,
		@NonNull ElGamalCiphertext eprime, @NonNull CivitasBigInteger c,
		@NonNull CivitasBigInteger w, @NonNull CivitasBigInteger r,
		@NonNull CivitasBigInteger u) {
}
