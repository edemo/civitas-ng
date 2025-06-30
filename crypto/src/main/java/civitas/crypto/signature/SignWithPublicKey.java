package civitas.crypto.signature;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;

import org.bouncycastle.crypto.CryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.ConvertPublicKeyToString;
import civitas.crypto.rsapublickey.ObtainRSASigner;

@Controller
public class SignWithPublicKey implements Constants {
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	ObtainRSASigner obtainRSASigner;
	@Autowired
	ConvertPublicKeyToString convertPublicKeyToString;

	public Signature apply(PrivateKey k, PublicKey publicKey, String hash)
			throws CryptoException {
		byte[] bytes = cryptoHash.apply(hash.getBytes());
		return apply(k, publicKey, bytes);
	}

	public Signature apply(PrivateKey k, PublicKey principal, byte[] bytes)
			throws CryptoException {
		java.security.Signature sig;
		try {
			sig = obtainRSASigner.apply();
			sig.initSign(k);
			sig.update(bytes);
			String pubKeyString = convertPublicKeyToString.apply(principal);
			return new Signature(sig.sign(), pubKeyString);
		} catch (NoSuchAlgorithmException | NoSuchProviderException
				| InvalidKeyException | SignatureException e) {
			throw new CryptoException();
		}
	}

}
