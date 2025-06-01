package civitas.crypto.messagedigest;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;

public class ObtainMessageDigest implements Constants {
	public MessageDigest apply() throws CryptoError {
		try {
			if (MESSAGE_DIGEST_PROVIDER == null) {
				return new MessageDigest(
						java.security.MessageDigest.getInstance(MESSAGE_DIGEST_ALG));
			} else {
				return new MessageDigest(java.security.MessageDigest
						.getInstance(MESSAGE_DIGEST_ALG, MESSAGE_DIGEST_PROVIDER));
			}
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoError(e);
		} catch (NoSuchProviderException e) {
			throw new CryptoError("No provider " + MESSAGE_DIGEST_PROVIDER);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot create message digest", e);
		}
	}

}
