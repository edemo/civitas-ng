/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.keyshare;

import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLog;
import civitas.crypto.publickey.ElGamalPublicKey;
import lombok.Data;
import lombok.NonNull;

@Data
public class ElGamalKeyShare {

	@NonNull
	public final ElGamalPublicKey pubKey;
	@NonNull
	public final ElGamalProofKnowDiscLog proof;

}
