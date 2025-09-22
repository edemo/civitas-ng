/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.proofdisclog;

import civitas.util.CivitasBigInteger;
import lombok.NonNull;

public record ElGamalProofDiscLogEquality(@NonNull CivitasBigInteger g1,
		@NonNull CivitasBigInteger g2, @NonNull CivitasBigInteger v,
		@NonNull CivitasBigInteger w, @NonNull CivitasBigInteger a,
		@NonNull CivitasBigInteger b, @NonNull CivitasBigInteger c,
		@NonNull CivitasBigInteger r) {
}
