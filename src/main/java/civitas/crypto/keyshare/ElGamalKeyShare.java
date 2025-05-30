/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.keyshare;

import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLog;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Use;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ElGamalKeyShare {

	@Use
	VerifyElGamalKeyShare verifyElGamalKeyShare;
	public final ElGamalPublicKey pubKey;
	public final ElGamalProofKnowDiscLog proof;

	public ElGamalKeyShare(ElGamalPublicKey pubKey,
			ElGamalProofKnowDiscLog proof) {
		this.pubKey = pubKey;
		this.proof = proof;
	}

}
