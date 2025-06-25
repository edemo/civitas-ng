package civitas.bboard.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;

@Service
public class VerifyBBPost {

	@Autowired
	VerifyPublicKeySignature verifyPublicKeySignature;
	@Autowired
	CryptoHash cryptoHash;

	public boolean apply(BBPost that) throws JsonProcessingException {
		byte[] hash = cryptoHash.apply(that.msg);
		return verifyPublicKeySignature.apply(that.sig, hash);
	}
}
