/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.proofknowndisclog;

import civitas.util.CivitasBigInteger;
import lombok.NonNull;

public record ElGamalProofKnowDiscLog(@NonNull CivitasBigInteger a,
		@NonNull CivitasBigInteger c, @NonNull CivitasBigInteger r,
		@NonNull CivitasBigInteger v) {
}
