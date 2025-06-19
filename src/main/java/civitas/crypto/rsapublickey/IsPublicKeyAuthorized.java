package civitas.crypto.rsapublickey;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.CreateFreshNonceBase64;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.rsaprivatekey.PrivateKey;
import civitas.crypto.signature.SignWithPublicKey;
import civitas.crypto.signature.Signature;

@Service
public class IsPublicKeyAuthorized implements Constants {

	@Autowired
	VerifyPublicKeySignature verifyPublicKeySignature;
	@Autowired
	SignWithPublicKey signWithPublicKey;
	@Autowired
	CreateFreshNonceBase64 createFreshNonceBase64;

	public boolean apply(PublicKey that, PrivateKey privKey) {
		PublicKeyMsg m = new PublicKeyMsg(
				createFreshNonceBase64.apply(AUTHENTICATION_NONCE_LENGTH));
		Signature sig;
		try {
			sig = signWithPublicKey.apply(privKey, m);
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchProviderException | SignatureException | CryptoError e) {
			return false;
		}
		return verifyPublicKeySignature.apply(that, sig, m);
	}

}
