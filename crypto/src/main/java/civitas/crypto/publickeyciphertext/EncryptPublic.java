package civitas.crypto.publickeyciphertext;

import java.security.PublicKey;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.external.DoCrypto;

@Controller
public class EncryptPublic implements Constants {
	@Autowired
	DoCrypto doCrypto;

	public PublicKeyCiphertext apply(PublicKey key, String msg) {
		byte[] encrypted = doCrypto.apply(PUBLIC_KEY_CIPHER_ALG,
				PUBLIC_KEY_PROVIDER, key, Cipher.ENCRYPT_MODE, msg.getBytes());
		return new PublicKeyCiphertext(encrypted);
	}

}
