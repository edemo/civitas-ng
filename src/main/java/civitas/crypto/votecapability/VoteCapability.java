/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.votecapability;

import civitas.crypto.msg.ElGamalMsg;
import civitas.util.CivitasBigInteger;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VoteCapability extends ElGamalMsg {

	public VoteCapability(CivitasBigInteger c) {
		super(c);
	}

}
