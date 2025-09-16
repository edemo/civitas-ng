/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import lombok.Data;

@Data
public class VoterEncCapabilityShares {
	public final int regTellerIndex;
	public final String name;
	public final int voterBlock;
	public final ElGamalSignedCiphertext[] encCapabilityShares;

}