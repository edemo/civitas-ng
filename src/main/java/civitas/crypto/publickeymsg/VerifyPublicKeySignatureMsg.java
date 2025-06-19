package civitas.crypto.publickeymsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.rsapublickey.PublicKey;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;
import civitas.crypto.signature.Signature;

@Service
public class VerifyPublicKeySignatureMsg {
	@Autowired
	VerifyPublicKeySignature verifyPublicKeySignature;

	public PublicKeyMsg apply(PublicKey K, Signature s, PublicKeyMsg msg) {
		if (verifyPublicKeySignature.apply(K, s, msg)) {
			return msg;
		}
		return null;
	}

}
