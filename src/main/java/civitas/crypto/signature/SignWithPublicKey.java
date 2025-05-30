package civitas.crypto.signature;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.messagedigest.ComputeMessageDigest;
import civitas.crypto.msg.PublicKeyMsgC;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.rsaprivatekey.PrivateKey;
import civitas.crypto.rsaprivatekey.PrivateKeyC;
import civitas.util.Use;

public class SignWithPublicKey implements Constants {
	@Use
	ComputeMessageDigest computeMessageDigest;

	public Signature apply(PrivateKey k, PublicKeyMsg msg) throws CryptoError {
		try {
			PublicKeyMsgC mc = (PublicKeyMsgC) msg;
			byte[] bytes = computeMessageDigest.apply(mc.m.getBytes());
			return apply(k, bytes);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot sign", e);
		}
	}

	public Signature apply(PrivateKey k, byte[] bytes) throws CryptoError {
		try {
			java.security.Signature sig = java.security.Signature
					.getInstance(PUBLIC_KEY_SIGNATURE_ALG, PUBLIC_KEY_PROVIDER);
			PrivateKeyC kc = (PrivateKeyC) k;
			sig.initSign(kc.k);
			sig.update(bytes);
			return new SignatureC(sig.sign());

		} catch (Exception e) {
			throw new CryptoError("Cannot sign", e);
		}
	}

}
