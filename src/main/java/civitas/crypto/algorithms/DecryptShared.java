package civitas.crypto.algorithms;

import javax.crypto.Cipher;

import civitas.crypto.CryptoError;
import civitas.crypto.SharedKey;
import civitas.crypto.SharedKeyCiphertext;
import civitas.crypto.SharedKeyMsg;
import civitas.crypto.concrete.SharedKeyC;
import civitas.crypto.concrete.SharedKeyCiphertextC;
import civitas.crypto.concrete.SharedKeyMsgC;
import civitas.util.Use;

public class DecryptShared implements Constants {
	@Use
	DoCrypto doCrypto;

	public SharedKeyMsg apply(SharedKey key, SharedKeyCiphertext ciphertext)
			throws CryptoError {
		SharedKeyC keyc = (SharedKeyC) key;
		SharedKeyCiphertextC ciphertextc = (SharedKeyCiphertextC) ciphertext;
		byte[] plaintext = doCrypto.apply(SHARED_KEY_CIPHER_ALG,
				SHARED_KEY_PROVIDER, keyc.k, Cipher.DECRYPT_MODE,
				ciphertextc.toBytes());
		return new SharedKeyMsgC(plaintext);
	}

}
