package civitas.crypto.rsapublickey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.signature.Signature;

@Service
public class VerifyPublicKeySignature implements Constants {
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	ObtainRSASigner obtainRSASigner;

	public boolean apply(PublicKey K, Signature s, PublicKeyMsg msg)
			throws CryptoError {
		try {
			PublicKeyMsg mc = msg;
			byte[] bytes = cryptoHash.apply(mc);
			return apply(K, s, bytes);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot verify signature", e);
		}
	}

	public boolean apply(PublicKey K, Signature s, byte[] bytes)
			throws CryptoError {
		try {
			java.security.Signature sig = obtainRSASigner.apply();
			PublicKey Kc = K;
			Signature sc = s;
			sig.initVerify(Kc.key);
			sig.update(bytes);
			return sig.verify(sc.signature);
		} catch (Exception e) {
			throw new CryptoError(e);
		}
	}

}
