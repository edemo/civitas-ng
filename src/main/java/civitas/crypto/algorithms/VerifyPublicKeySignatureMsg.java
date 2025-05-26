package civitas.crypto.algorithms;

import civitas.crypto.PublicKey;
import civitas.crypto.PublicKeyMsg;
import civitas.crypto.Signature;
import civitas.util.Use;

public class VerifyPublicKeySignatureMsg {
	@Use
	VerifyPublicKeySignature verifyPublicKeySignature;

	public PublicKeyMsg apply(PublicKey K, Signature s, PublicKeyMsg msg) {
		if (verifyPublicKeySignature.apply(K, s, msg)) {
			return msg;
		}
		return null;
	}

}
