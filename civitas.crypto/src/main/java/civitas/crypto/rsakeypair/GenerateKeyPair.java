package civitas.crypto.rsakeypair;

import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;
import civitas.crypto.algorithms.CreateFreshNonceBase64;

@Controller
public class GenerateKeyPair {

	@Autowired
	CryptoBase cryptoBase;

	@Autowired
	CreateFreshNonceBase64 createFreshNonceBase64;

	public KeyPair apply(final int keyLength) {
		java.security.KeyPair kp = cryptoBase.getPublicKeyGenerator(keyLength).generateKeyPair();
		PublicKey pubk = kp.getPublic();
		java.security.PrivateKey prvk = kp.getPrivate();

		return new KeyPair(pubk, prvk);
	}
}
