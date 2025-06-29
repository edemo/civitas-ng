package civitas.crypto.rsapublickey;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.CreateFreshNonceBase64;
import civitas.crypto.signature.SignWithPublicKey;
import civitas.crypto.signature.Signature;

@Controller
public class IsPublicKeyAuthorized implements Constants {

	@Autowired
	VerifyPublicKeySignature verifyPublicKeySignature;
	@Autowired
	SignWithPublicKey signWithPublicKey;
	@Autowired
	CreateFreshNonceBase64 createFreshNonceBase64;

	public boolean apply(PublicKey that, PrivateKey privKey) {
		String m = createFreshNonceBase64.apply(AUTHENTICATION_NONCE_LENGTH);
		Signature sig;
		try {
			sig = signWithPublicKey.apply(privKey, that, m);
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchProviderException | SignatureException | CryptoError e) {
			return false;
		}
		return verifyPublicKeySignature.apply(sig, that, m);
	}

}
