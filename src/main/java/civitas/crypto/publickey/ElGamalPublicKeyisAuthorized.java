package civitas.crypto.publickey;

import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;

public class ElGamalPublicKeyisAuthorized {

	public boolean apply(ElGamalPublicKey that, Object prf) {
		if (prf instanceof ElGamalPrivateKey) {
			ElGamalPrivateKey k = (ElGamalPrivateKey) prf;
			ElGamalParameters param = that.params;
			return that.y.equals(param.g.modPow(k.x, param.p));
		}
		return false;
	}

}
