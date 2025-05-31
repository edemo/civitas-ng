package civitas.crypto.rsakeypair;

import civitas.crypto.algorithms.CreateFreshNonceBase64;
import civitas.crypto.algorithms.GetPublicKeyGenerator;
import civitas.crypto.rsaprivatekey.PrivateKey;
import civitas.crypto.rsapublickey.PublicKey;
import civitas.util.Use;

public class GenerateKeyPair {

	@Use
	GetPublicKeyGenerator getPublicKeyGenerator;
	@Use
	CreateFreshNonceBase64 createFreshNonceBase64;

	public KeyPair apply(int keyLength) {
		java.security.KeyPair kp = getPublicKeyGenerator.apply(keyLength)
				.generateKeyPair();
		java.security.PublicKey pubk = kp.getPublic();
		java.security.PrivateKey prvk = kp.getPrivate();

		return new KeyPair(
				new PublicKey(pubk, "keypair-" + createFreshNonceBase64.apply(64)),
				new PrivateKey(prvk));
	}

}
