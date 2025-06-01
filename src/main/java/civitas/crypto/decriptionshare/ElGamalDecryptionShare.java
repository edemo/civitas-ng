/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.decriptionshare;

import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import civitas.util.CivitasBigInteger;
import lombok.Data;
import lombok.NonNull;

@Data
public class ElGamalDecryptionShare {

	@NonNull
	public final CivitasBigInteger ai;
	@NonNull
	public final ElGamalProofDiscLogEquality proof;

}
