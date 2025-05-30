package civitas.crypto.sharedkey;

import javax.crypto.Cipher;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.external.DoCrypto;
import civitas.crypto.sharedkeyciphertext.SharedKeyCiphertext;
import civitas.crypto.sharedkeyciphertext.SharedKeyCiphertextC;
import civitas.crypto.sharedkeymsg.SharedKeyMsg;
import civitas.crypto.sharedkeymsg.SharedKeyMsgC;
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
