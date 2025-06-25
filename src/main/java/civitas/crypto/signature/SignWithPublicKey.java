package civitas.crypto.signature;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsaprivatekey.PrivateKey;
import civitas.crypto.rsapublickey.ObtainRSASigner;
import civitas.util.KeyOnWire;

@Service
public class SignWithPublicKey implements Constants {
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	ObtainRSASigner obtainRSASigner;

	public Signature apply(PrivateKey k, KeyOnWire principal, String msg)
			throws CryptoError, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException {
		byte[] bytes = cryptoHash.apply(msg.getBytes());
		return apply(k, principal, bytes);
	}

	public Signature apply(PrivateKey k, KeyOnWire principal, byte[] bytes)
			throws CryptoError, NoSuchAlgorithmException, NoSuchProviderException,
			InvalidKeyException, SignatureException {
		java.security.Signature sig = obtainRSASigner.apply();
		sig.initSign(k.k);
		sig.update(bytes);
		return new Signature(sig.sign(), principal);
	}

}
