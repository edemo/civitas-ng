package civitas.crypto.rsaprivatekey;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import civitas.crypto.CryptoError;
import civitas.crypto.CryptoFactoryC;

public class CreatePrivateKeyFromBytes {
	public java.security.PrivateKey apply(byte[] bs) throws CryptoError {
		KeySpec keySpec = new PKCS8EncodedKeySpec(bs);
		try {
			return CryptoFactoryC.publicKeyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			throw new CryptoError(e);
		}
	}

}
