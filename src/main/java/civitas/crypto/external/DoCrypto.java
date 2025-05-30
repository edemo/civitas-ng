package civitas.crypto.external;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import civitas.crypto.CryptoError;

public class DoCrypto {
	public byte[] apply(String alg, String provider, Key skey, int mode,
			byte[] input) throws CryptoError {
		// Instantiate the cipher
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(alg, provider);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoError("Cannot find algorithm " + alg, e);
		} catch (NoSuchPaddingException e) {
			throw new CryptoError("Cannot find algorithm " + alg, e);
		} catch (NoSuchProviderException e) {
			throw new CryptoError("Cannot find provider " + provider, e);
		} catch (RuntimeException e) {
			throw new CryptoError("Cannot create cipher", e);
		}

		try {
			cipher.init(mode, skey);
		} catch (InvalidKeyException e) {
			throw new CryptoError(
					"Invalid key.  May need to install unlimited strength crypto policies.",
					e);
		}

		byte[] output;
		try {
			output = cipher.doFinal(input);
		} catch (IllegalBlockSizeException e) {
			throw new CryptoError("Illegal block size", e);
		} catch (BadPaddingException e) {
			throw new CryptoError("bad padding", e);
		} catch (RuntimeException e) {
			throw new CryptoError(e);
		}
		return output;
	}

}
