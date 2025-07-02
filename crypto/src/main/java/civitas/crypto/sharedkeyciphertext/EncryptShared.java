package civitas.crypto.sharedkeyciphertext;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.external.DoCrypto;
import civitas.crypto.sharedkey.SharedKey;
import civitas.crypto.sharedkeymsg.SharedKeyMsg;

@Controller
public class EncryptShared implements Constants {
	@Autowired
	DoCrypto doCrypto;

	public SharedKeyCiphertext apply(SharedKey key, SharedKeyMsg msg) {
		SharedKey keyc = key;
		SharedKeyMsg msgc = msg;
		byte[] encrypted = doCrypto.apply(SHARED_KEY_CIPHER_ALG,
				SHARED_KEY_PROVIDER, keyc.k, Cipher.ENCRYPT_MODE, msgc.m.getBytes());
		return new SharedKeyCiphertext(encrypted);
	}

}
