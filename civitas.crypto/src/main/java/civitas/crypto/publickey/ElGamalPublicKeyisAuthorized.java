package civitas.crypto.publickey;

import org.springframework.stereotype.Controller;

import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;

@Controller
public class ElGamalPublicKeyisAuthorized {

	public boolean apply(final ElGamalPublicKey that, final Object prf) {
		if (prf instanceof ElGamalPrivateKey) {
			ElGamalPrivateKey k = (ElGamalPrivateKey) prf;
			ElGamalParameters param = that.params;
			return that.y.equals(param.g.modPow(k.x(), param.p));
		}
		return false;
	}
}
