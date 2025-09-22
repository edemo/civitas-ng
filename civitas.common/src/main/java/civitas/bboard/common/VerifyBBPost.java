package civitas.bboard.common;

import org.bouncycastle.crypto.CryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;

@Controller
public class VerifyBBPost {

	@Autowired
	VerifyPublicKeySignature verifyPublicKeySignature;

	@Autowired
	CryptoHash cryptoHash;

	public boolean apply(BBPost that) throws CryptoException {
		byte[] hash = cryptoHash.apply(that.msg.getBytes());
		return verifyPublicKeySignature.apply(that.sig, hash);
	}
}
