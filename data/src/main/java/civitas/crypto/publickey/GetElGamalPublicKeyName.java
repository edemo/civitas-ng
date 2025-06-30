package civitas.crypto.publickey;

import org.springframework.stereotype.Controller;

import civitas.common.Util;

@Controller
public class GetElGamalPublicKeyName {
	public String apply(ElGamalPublicKey that) {
		return "ElGamalPublicKey-" + Util.fromBigInt(that.y);
	}

}
