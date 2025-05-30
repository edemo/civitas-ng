package civitas.crypto.publickeymsg;

import civitas.crypto.publickey.PublicKey;
import civitas.crypto.publickey.VerifyPublicKeySignature;
import civitas.crypto.signature.Signature;
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
