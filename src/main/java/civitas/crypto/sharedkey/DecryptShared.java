package civitas.crypto.sharedkey;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.external.DoCrypto;
import civitas.crypto.sharedkeyciphertext.SharedKeyCiphertext;
import civitas.crypto.sharedkeymsg.SharedKeyMsg;

@Service
public class DecryptShared implements Constants {
	@Autowired
	DoCrypto doCrypto;

	public SharedKeyMsg apply(SharedKey key, SharedKeyCiphertext ciphertext)
			throws CryptoError, UnsupportedEncodingException {
		SharedKey keyc = key;
		SharedKeyCiphertext ciphertextc = ciphertext;
		byte[] plaintext = doCrypto.apply(SHARED_KEY_CIPHER_ALG,
				SHARED_KEY_PROVIDER, keyc.k, Cipher.DECRYPT_MODE,
				ciphertextc.encryptedBytes);
		return new SharedKeyMsg(new String(plaintext, Constants.CHARSET_NAME));
	}

}
