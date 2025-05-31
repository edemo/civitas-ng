package civitas.crypto.rsapublickey;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.messagedigest.ComputeMessageDigest;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.signature.Signature;
import civitas.util.Use;

public class VerifyPublicKeySignature implements Constants {
	@Use
	ComputeMessageDigest computeMessageDigest;

	public boolean apply(PublicKey K, Signature s, PublicKeyMsg msg)
			throws CryptoError {
		try {
			PublicKeyMsg mc = msg;
			byte[] bytes = computeMessageDigest.apply(mc.m.getBytes());
			return apply(K, s, bytes);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot verify signature", e);
		}
	}

	public boolean apply(PublicKey K, Signature s, byte[] bytes)
			throws CryptoError {
		try {
			java.security.Signature sig = java.security.Signature
					.getInstance(PUBLIC_KEY_SIGNATURE_ALG, PUBLIC_KEY_PROVIDER);
			PublicKey Kc = K;
			Signature sc = s;
			sig.initVerify(Kc.k);
			sig.update(bytes);
			return sig.verify(sc.signature);
		} catch (Exception e) {
			throw new CryptoError(e);
		}
	}

}
