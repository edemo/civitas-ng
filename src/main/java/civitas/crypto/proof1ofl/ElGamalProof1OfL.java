/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.proof1ofl;

import civitas.util.CivitasBigInteger;
import lombok.Data;

@Data
public class ElGamalProof1OfL {

	public final int L;
	public final CivitasBigInteger[] dvs;
	public final CivitasBigInteger[] rvs;

}
