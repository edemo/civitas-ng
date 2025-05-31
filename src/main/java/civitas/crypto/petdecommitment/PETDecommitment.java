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
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class PETDecommitment implements Constants {
	@NonNull
	public final CivitasBigInteger di;
	@NonNull
	public final CivitasBigInteger ei;
	@NonNull
	public final ElGamalProofDiscLogEquality proof;

}