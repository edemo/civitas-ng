package civitas.crypto.rsapublickey;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

import civitas.crypto.CryptoError;
import civitas.crypto.CryptoFactoryC;

public class CreatePublicKeyFromBytes {

	public java.security.PublicKey apply(byte[] bs) throws CryptoError {
		KeySpec keySpec = new X509EncodedKeySpec(bs);
		try {
			return CryptoFactoryC.publicKeyFactory.generatePublic(keySpec);
		} catch (InvalidKeySpecException e) {
			throw new CryptoError(e);
		}
	}

}
