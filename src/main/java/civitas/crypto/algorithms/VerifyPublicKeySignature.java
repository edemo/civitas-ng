package civitas.crypto.algorithms;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

import civitas.crypto.CryptoError;
import civitas.crypto.PublicKey;
import civitas.crypto.PublicKeyMsg;
import civitas.crypto.Signature;
import civitas.crypto.concrete.PublicKeyC;
import civitas.crypto.concrete.PublicKeyMsgC;
import civitas.crypto.concrete.SignatureC;
import civitas.external.ComputeMessageDigest;
import civitas.util.Use;

public class VerifyPublicKeySignature implements Constants {
	@Use
	ComputeMessageDigest computeMessageDigest;

	public boolean apply(PublicKey K, Signature s, PublicKeyMsg msg)
			throws CryptoError {
		try {
			PublicKeyMsgC mc = (PublicKeyMsgC) msg;
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
			PublicKeyC Kc = (PublicKeyC) K;
			SignatureC sc = (SignatureC) s;
			sig.initVerify(Kc.k);
			sig.update(bytes);
			return sig.verify(sc.signature);
		} catch (InvalidKeyException e) {
			throw new CryptoError(e);
		} catch (SignatureException e) {
			throw new CryptoError(e);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoError(e);
		} catch (NoSuchProviderException e) {
			throw new CryptoError(e);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot verify signature", e);
		}
	}

}
