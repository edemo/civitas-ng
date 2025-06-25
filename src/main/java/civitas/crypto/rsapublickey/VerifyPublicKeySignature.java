package civitas.crypto.rsapublickey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.signature.Signature;

@Service
public class VerifyPublicKeySignature implements Constants {
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	ObtainRSASigner obtainRSASigner;
	@Autowired
	CreatePublicKeyFromWire createPublicKeyFromBytes;

	public boolean apply(Signature s, String msg) throws CryptoError {
		try {
			byte[] bytes = cryptoHash.apply(msg);
			return apply(s, bytes);
		} catch (RuntimeException e) {
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

	public boolean apply(Signature s, byte[] bytes) throws CryptoError {
		PublicKey pubkey = createPublicKeyFromBytes.apply(s.getSigner());
		return apply(s, pubkey, bytes);
	}

	public boolean apply(Signature s, PublicKey signer, byte[] bytes)
			throws CryptoError {
		try {
			java.security.Signature sig = obtainRSASigner.apply();
			PublicKey Kc = signer;
			Signature sc = s;
			sig.initVerify(Kc.key);
			sig.update(bytes);
			return sig.verify(sc.signature);
		} catch (Exception e) {
			throw new CryptoError(e);
		}
	}

}
