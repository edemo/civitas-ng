package civitas.crypto.sharedkeyciphertext;

import javax.crypto.Cipher;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.external.DoCrypto;
import civitas.crypto.sharedkey.SharedKey;
import civitas.crypto.sharedkeymsg.SharedKeyMsg;
import civitas.util.Use;

public class EncryptShared implements Constants {
	@Use
	DoCrypto doCrypto;

	public SharedKeyCiphertext apply(SharedKey key, SharedKeyMsg msg)
			throws CryptoError {
		SharedKey keyc = key;
		SharedKeyMsg msgc = msg;
		byte[] encrypted = doCrypto.apply(SHARED_KEY_CIPHER_ALG,
				SHARED_KEY_PROVIDER, keyc.k, Cipher.ENCRYPT_MODE, msgc.m.getBytes());
		return new SharedKeyCiphertext(encrypted);
	}

}
