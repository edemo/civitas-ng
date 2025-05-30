/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.publickey;

import civitas.common.Util;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.util.CivitasBigInteger;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ElGamalPublicKey {
	public final static String EG_OPENING_TAG = "elGamalPublicKey";

	public final CivitasBigInteger y;
	public final ElGamalParameters params;

	public ElGamalPublicKey(CivitasBigInteger y, ElGamalParameters params) {
		this.y = y;
		this.params = params;
	}

	public boolean isAuthorized(Object prf) {
		// check if prf is the matching ElGamalPrivateKey
		if (prf instanceof ElGamalPrivateKey) {
			ElGamalPrivateKey k = (ElGamalPrivateKey) prf;
			ElGamalParametersC param = (ElGamalParametersC) this.params;
			return y.equals(param.g.modPow(k.x, param.p));
		}
		return false;
	}

	public String name() {
		return "ElGamalPublicKey-" + Util.fromBigInt(y);
	}

}
