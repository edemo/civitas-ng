package civitas.bboard.common;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import civitas.crypto.CryptoError;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;

@Controller
public class VerifyBBPost {

	@Autowired
	VerifyPublicKeySignature verifyPublicKeySignature;
	@Autowired
	CryptoHash cryptoHash;

	public boolean apply(BBPost that) throws JsonProcessingException,
			NoSuchAlgorithmException, InvalidKeySpecException, CryptoError {
		byte[] hash = cryptoHash.apply(that.msg);
		return verifyPublicKeySignature.apply(that.sig, hash);
	}
}
