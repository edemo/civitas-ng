/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.parameters;

import civitas.util.CivitasBigInteger;
import lombok.NonNull;

public record ElGamalParameters(@NonNull CivitasBigInteger p,
		@NonNull CivitasBigInteger q, @NonNull CivitasBigInteger g) {
}
