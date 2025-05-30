/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto;

public abstract class ElGamalAbstractKeyShare implements ElGamalKeyShare {
	public final ElGamalPublicKey pubKey;
	public final ElGamalProofKnowDiscLog proof;

	protected ElGamalAbstractKeyShare(ElGamalPublicKey pubKey,
			ElGamalProofKnowDiscLog proof) {
		this.pubKey = pubKey;
		this.proof = proof;
	}

	@Override
	public ElGamalPublicKey pubKey() {
		return pubKey;
	}

	@Override
	public ElGamalProofKnowDiscLog proof() {
		return proof;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ElGamalAbstractKeyShare)) {
			return false;
		}
		if (null == this.proof) {
			return false;
		}
		if (null == this.pubKey) {
			return false;
		}

		ElGamalAbstractKeyShare z = (ElGamalAbstractKeyShare) o;
		return this.pubKey.equals(z.pubKey) && this.proof.equals(z.proof);
	}

}
