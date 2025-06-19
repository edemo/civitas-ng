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
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.rsaprivatekey.PrivateKey;
import civitas.crypto.rsapublickey.ObtainRSASigner;

@Service
public class SignWithPublicKey implements Constants {
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	ObtainRSASigner obtainRSASigner;

	public Signature apply(PrivateKey k, PublicKeyMsg msg)
			throws CryptoError, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException {
		PublicKeyMsg mc = msg;
		byte[] bytes = cryptoHash.apply(mc.m.getBytes());
		return apply(k, bytes);
	}

	public Signature apply(PrivateKey k, byte[] bytes)
			throws CryptoError, NoSuchAlgorithmException, NoSuchProviderException,
			InvalidKeyException, SignatureException {
		java.security.Signature sig = obtainRSASigner.apply();
		sig.initSign(k.k);
		sig.update(bytes);
		return new Signature(sig.sign());
	}

}
