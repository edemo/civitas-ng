package civitas.crypto.sharedkeyciphertext;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import civitas.crypto.sharedkey.SharedKey;
import civitas.crypto.sharedkeymsg.SharedKeyMsg;

@Controller
public class EncryptShared implements Constants {
	@Autowired
	CryptoBase cryptoBase;

	public SharedKeyCiphertext apply(final SharedKey key, final SharedKeyMsg msg) {
		SharedKey keyc = key;
		SharedKeyMsg msgc = msg;
		byte[] encrypted = cryptoBase.doCrypto(
				SHARED_KEY_CIPHER_ALG,
				SHARED_KEY_PROVIDER,
				keyc.k(),
				Cipher.ENCRYPT_MODE,
				msgc.m().getBytes());
		return new SharedKeyCiphertext(encrypted);
	}
}
