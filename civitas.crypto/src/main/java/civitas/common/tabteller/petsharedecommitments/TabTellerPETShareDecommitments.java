/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.tabteller.petsharedecommitments;

import civitas.crypto.petdecommitment.PETDecommitment;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import lombok.Data;

@Data
public class TabTellerPETShareDecommitments {
	public final int tellerIndex;
	public final PETDecommitment[] decommitments;
	public final ElGamalProofDiscLogEquality[] proofs;
}
