package civitas.crypto.external;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.Constants;
import civitas.crypto.messagedigest.MessageDigest;
import civitas.crypto.messagedigest.MessageDigestC;

public class ObtainMessageDigest implements Constants {
	public MessageDigest apply() throws CryptoError {
		try {
			if (MESSAGE_DIGEST_PROVIDER == null) {
				return new MessageDigestC(
						java.security.MessageDigest.getInstance(MESSAGE_DIGEST_ALG));
			} else {
				return new MessageDigestC(java.security.MessageDigest
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
