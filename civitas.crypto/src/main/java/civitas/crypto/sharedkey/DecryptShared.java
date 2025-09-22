package civitas.crypto.sharedkey;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import civitas.crypto.sharedkeyciphertext.SharedKeyCiphertext;
import civitas.crypto.sharedkeymsg.SharedKeyMsg;

@Controller
public class DecryptShared implements Constants {
	@Autowired
	CryptoBase cryptoBase;

	public SharedKeyMsg apply(final SharedKey key, final SharedKeyCiphertext ciphertext) {
		byte[] plaintext = cryptoBase.doCrypto(
				SHARED_KEY_CIPHER_ALG, SHARED_KEY_PROVIDER, key.k(), Cipher.DECRYPT_MODE, ciphertext.encryptedBytes);
		return new SharedKeyMsg(new String(plaintext, CHARSET));
	}
}
