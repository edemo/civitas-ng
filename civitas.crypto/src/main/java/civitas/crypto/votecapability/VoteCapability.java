/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.votecapability;

import civitas.crypto.msg.CryptMessage;
import civitas.util.CivitasBigInteger;
import lombok.Data;
import lombok.NonNull;

@Data
public class VoteCapability implements CryptMessage {
	@NonNull
	public final CivitasBigInteger m;

}
