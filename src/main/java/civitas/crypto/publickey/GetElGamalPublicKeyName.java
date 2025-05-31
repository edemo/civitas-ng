package civitas.crypto.publickey;

import civitas.common.Util;

public class GetElGamalPublicKeyName {
	public String apply(ElGamalPublicKey that) {
		return "ElGamalPublicKey-" + Util.fromBigInt(that.y);
	}

}
