package civitas.crypto.external;

import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import civitas.crypto.CryptoError;
import civitas.crypto.CryptoFactoryC;
import civitas.crypto.algorithms.Constants;

public class CreateSharedKeyFromBytes implements Constants {
	public SecretKey apply(byte[] bs) throws CryptoError {
		SecretKeySpec skeySpec = new SecretKeySpec(bs, SHARED_KEY_ALG);
		try {
			return CryptoFactoryC.sharedKeyFactory.generateSecret(skeySpec);
		} catch (InvalidKeySpecException e) {
			throw new CryptoError(e);
		}
	}

}
