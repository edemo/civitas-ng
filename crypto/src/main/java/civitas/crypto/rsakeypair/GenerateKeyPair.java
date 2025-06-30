package civitas.crypto.rsakeypair;

import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.algorithms.CreateFreshNonceBase64;
import civitas.crypto.algorithms.GetPublicKeyGenerator;

@Controller
public class GenerateKeyPair {

	@Autowired
	GetPublicKeyGenerator getPublicKeyGenerator;
	@Autowired
	CreateFreshNonceBase64 createFreshNonceBase64;

	public KeyPair apply(int keyLength) {
		java.security.KeyPair kp = getPublicKeyGenerator.apply(keyLength)
				.generateKeyPair();
		PublicKey pubk = kp.getPublic();
		java.security.PrivateKey prvk = kp.getPrivate();

		return new KeyPair(pubk, prvk);
	}

}
