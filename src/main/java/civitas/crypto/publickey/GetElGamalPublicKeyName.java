package civitas.crypto.publickey;

import org.springframework.stereotype.Service;

import civitas.common.Util;

@Service
public class GetElGamalPublicKeyName {
	public String apply(ElGamalPublicKey that) {
		return "ElGamalPublicKey-" + Util.fromBigInt(that.y);
	}

}
