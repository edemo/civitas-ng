package civitas.crypto.algorithms;

import civitas.crypto.KeyPair;
import civitas.crypto.concrete.PrivateKeyC;
import civitas.crypto.concrete.PublicKeyC;
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
				new PublicKeyC(pubk, "keypair-" + createFreshNonceBase64.apply(64)),
				new PrivateKeyC(prvk));
	}

}
