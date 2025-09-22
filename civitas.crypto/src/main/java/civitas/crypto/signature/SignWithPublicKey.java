package civitas.crypto.signature;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;

import org.bouncycastle.crypto.CryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.ConvertPublicKeyToString;

@Controller
public class SignWithPublicKey implements Constants {
	@Autowired
	CryptoHash cryptoHash;

	@Autowired
	CryptoBase cryptoBase;

	@Autowired
	ConvertPublicKeyToString convertPublicKeyToString;

	public Signature apply(PrivateKey k, PublicKey publicKey, String hash) throws CryptoException {
		byte[] bytes = cryptoHash.apply(hash.getBytes());
		return apply(k, publicKey, bytes);
	}

	public Signature apply(PrivateKey k, PublicKey principal, byte[] bytes) throws CryptoException {
		try {
			cryptoBase.rsaSigner.initSign(k);
			cryptoBase.rsaSigner.update(bytes);
			byte[] signature = cryptoBase.rsaSigner.sign();
			String pubKeyString = convertPublicKeyToString.apply(principal);
			return new Signature(signature, pubKeyString);
		} catch (InvalidKeyException | SignatureException e) {
			throw new CryptoException("cannot sign", e);
		}
	}
}
