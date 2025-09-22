package civitas.crypto.rsapublickey;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.crypto.CryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
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

	public boolean apply(PublicKey that, PrivateKey privKey) throws CryptoException {
		String m = createFreshNonceBase64.apply(AUTHENTICATION_NONCE_LENGTH);
		Signature sig;
		sig = signWithPublicKey.apply(privKey, that, m);
		return verifyPublicKeySignature.apply(sig, that, m);
	}
}
