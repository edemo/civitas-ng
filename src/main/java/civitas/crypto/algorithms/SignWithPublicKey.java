package civitas.crypto.algorithms;

import civitas.crypto.CryptoError;
import civitas.crypto.PrivateKey;
import civitas.crypto.PublicKeyMsg;
import civitas.crypto.Signature;
import civitas.crypto.concrete.PrivateKeyC;
import civitas.crypto.concrete.PublicKeyMsgC;
import civitas.crypto.concrete.SignatureC;
import civitas.external.ComputeMessageDigest;
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
