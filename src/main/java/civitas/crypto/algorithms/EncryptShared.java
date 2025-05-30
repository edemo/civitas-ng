package civitas.crypto.algorithms;

import javax.crypto.Cipher;

import civitas.crypto.CryptoError;
import civitas.crypto.SharedKey;
import civitas.crypto.SharedKeyCiphertext;
import civitas.crypto.SharedKeyMsg;
import civitas.crypto.concrete.SharedKeyC;
import civitas.crypto.concrete.SharedKeyCiphertextC;
import civitas.crypto.concrete.SharedKeyMsgC;
import civitas.external.DoCrypto;
import civitas.util.Use;

public class EncryptShared implements Constants {
	@Use
	DoCrypto doCrypto;

	public SharedKeyCiphertext apply(SharedKey key, SharedKeyMsg msg)
			throws CryptoError {
		SharedKeyC keyc = (SharedKeyC) key;
		SharedKeyMsgC msgc = (SharedKeyMsgC) msg;
		byte[] encrypted = doCrypto.apply(SHARED_KEY_CIPHER_ALG,
				SHARED_KEY_PROVIDER, keyc.k, Cipher.ENCRYPT_MODE, msgc.toBytes());
		return new SharedKeyCiphertextC(encrypted);
	}

}
