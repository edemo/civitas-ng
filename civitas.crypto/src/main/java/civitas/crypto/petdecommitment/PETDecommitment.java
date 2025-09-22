/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.petdecommitment;

import civitas.crypto.Constants;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import civitas.util.CivitasBigInteger;
import lombok.NonNull;

public record PETDecommitment(@NonNull CivitasBigInteger di,
		@NonNull CivitasBigInteger ei, @NonNull ElGamalProofDiscLogEquality proof)
		implements Constants {
}
