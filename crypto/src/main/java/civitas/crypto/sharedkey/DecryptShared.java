package civitas.crypto.sharedkey;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import civitas.crypto.CryptoError;
import civitas.crypto.sharedkeyciphertext.SharedKeyCiphertext;
import civitas.crypto.sharedkeymsg.SharedKeyMsg;

@Controller
public class DecryptShared implements Constants {
	@Autowired
	CryptoBase cryptoBase;

	public SharedKeyMsg apply(SharedKey key, SharedKeyCiphertext ciphertext)
			throws CryptoError, UnsupportedEncodingException {
		SharedKey keyc = key;
		SharedKeyCiphertext ciphertextc = ciphertext;
		byte[] plaintext = cryptoBase.doCrypto(SHARED_KEY_CIPHER_ALG,
				SHARED_KEY_PROVIDER, keyc.k, Cipher.DECRYPT_MODE,
				ciphertextc.encryptedBytes);
		return new SharedKeyMsg(new String(plaintext, Constants.CHARSET_NAME));
	}

}
