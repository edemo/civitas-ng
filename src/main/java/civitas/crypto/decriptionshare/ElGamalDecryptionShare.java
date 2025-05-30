/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.decriptionshare;

import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityC;
import civitas.util.CivitasBigInteger;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class ElGamalDecryptionShare {

	@NonNull
	public final CivitasBigInteger ai;
	@NonNull
	public final ElGamalProofDiscLogEqualityC proof;

}
