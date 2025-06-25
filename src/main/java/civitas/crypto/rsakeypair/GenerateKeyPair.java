package civitas.crypto.rsakeypair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.algorithms.CreateFreshNonceBase64;
import civitas.crypto.algorithms.GetPublicKeyGenerator;
import civitas.crypto.rsaprivatekey.PrivateKey;
import civitas.crypto.rsapublickey.PublicKey;

@Service
public class GenerateKeyPair {

	@Autowired
	GetPublicKeyGenerator getPublicKeyGenerator;
	@Autowired
	CreateFreshNonceBase64 createFreshNonceBase64;

	public KeyPair apply(int keyLength) {
		java.security.KeyPair kp = getPublicKeyGenerator.apply(keyLength)
				.generateKeyPair();
		java.security.PublicKey pubk = kp.getPublic();
		java.security.PrivateKey prvk = kp.getPrivate();

		return new KeyPair(new PublicKey(pubk), new PrivateKey(prvk));
	}

}
