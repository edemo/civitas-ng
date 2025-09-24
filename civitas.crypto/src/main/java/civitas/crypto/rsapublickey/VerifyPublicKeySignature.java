package civitas.crypto.rsapublickey;

import java.security.PublicKey;

import org.bouncycastle.crypto.CryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.signature.Signature;

@Controller
public class VerifyPublicKeySignature implements Constants {
	@Autowired
	CryptoHash cryptoHash;

	@Autowired
	CryptoBase cryptoBase;

	@Autowired
	ConvertStringToPublicKey convertStringToPublicKey;

	public boolean apply(final Signature s, final String msg) throws CryptoException {
		byte[] bytes = cryptoHash.apply(msg.getBytes());
		return apply(s, bytes);
	}

	public boolean apply(final Signature s, final PublicKey pubKey, final String msg) throws CryptoException {
		byte[] bytes = cryptoHash.apply(msg.getBytes());
		return apply(s, pubKey, bytes);
	}

	public boolean apply(final Signature s, final byte[] bytes) throws CryptoException {
		String pubkeyString = s.getSignerPubKey();
		PublicKey pubKey = convertStringToPublicKey.apply(pubkeyString);
		return apply(s, pubKey, bytes);
	}

	public boolean apply(final Signature s, final PublicKey signer, final byte[] bytes) throws CryptoException {
		try {
			cryptoBase.rsaSigner.initVerify(signer);
			cryptoBase.rsaSigner.update(bytes);
			return cryptoBase.rsaSigner.verify(s.signatureBytes);
		} catch (Exception e) {
			throw new CryptoException("cannot verify signature", e);
		}
	}
}
