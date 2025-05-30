package civitas.crypto.external;

import civitas.crypto.algorithms.CreateFreshNonceBase64;
import civitas.crypto.algorithms.GetPublicKeyGenerator;
import civitas.crypto.keypair.KeyPair;
import civitas.crypto.privatekey.PrivateKeyC;
import civitas.crypto.publickey.PublicKeyC;
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
