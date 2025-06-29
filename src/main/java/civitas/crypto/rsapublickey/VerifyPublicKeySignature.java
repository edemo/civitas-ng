package civitas.crypto.rsapublickey;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.signature.Signature;

@Controller
public class VerifyPublicKeySignature implements Constants {
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	ObtainRSASigner obtainRSASigner;
	@Autowired
	ConvertStringToPublicKey convertStringToPublicKey;

	public boolean apply(Signature s, String msg) throws CryptoError {
		try {
			byte[] bytes = cryptoHash.apply(msg);
			return apply(s, bytes);
		} catch (Exception e) {
			throw new CryptoError("Cannot verify signature", e);
		}
	}

	public boolean apply(Signature s, PublicKey pubKey, String msg)
			throws CryptoError {
		try {
			byte[] bytes = cryptoHash.apply(msg);
			return apply(s, pubKey, bytes);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot verify signature", e);
		}
	}

	public boolean apply(Signature s, byte[] bytes)
			throws CryptoError, NoSuchAlgorithmException, InvalidKeySpecException {
		String pubkeyString = s.getSignerPubKey();
		PublicKey pubKey = convertStringToPublicKey.apply(pubkeyString);
		return apply(s, pubKey, bytes);
	}

	public boolean apply(Signature s, PublicKey signer, byte[] bytes)
			throws CryptoError {
		try {
			java.security.Signature sig = obtainRSASigner.apply();
			PublicKey Kc = signer;
			Signature sc = s;
			sig.initVerify(Kc);
			sig.update(bytes);
			return sig.verify(sc.signature);
		} catch (Exception e) {
			throw new CryptoError(e);
		}
	}

}
