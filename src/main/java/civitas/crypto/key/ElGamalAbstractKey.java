/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.key;

import civitas.crypto.parameters.ElGamalParameters;

public abstract class ElGamalAbstractKey implements ElGamalKey {
	public final ElGamalParameters params;

	protected ElGamalAbstractKey(ElGamalParameters params) {
		this.params = params;
	}

	@Override
	public ElGamalParameters getParams() {
		return params;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ElGamalAbstractKey)) {
			return false;
		}

		ElGamalAbstractKey x = (ElGamalAbstractKey) o;
		return this.params.equals(x.params);
	}
}
