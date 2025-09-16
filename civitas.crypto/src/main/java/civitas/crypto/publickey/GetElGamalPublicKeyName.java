package civitas.crypto.publickey;

import org.springframework.stereotype.Controller;

import civitas.common.CommonUtil;

@Controller
public class GetElGamalPublicKeyName {
	public String apply(final ElGamalPublicKey that) {
		return "ElGamalPublicKey-" + CommonUtil.fromBigInt(that.y());
	}

}
