package civitas.crypto.publickeyciphertext;

import java.security.PublicKey;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;

@Controller
public class EncryptPublic implements Constants {
	@Autowired
	CryptoBase cryptoBase;

	public PublicKeyCiphertext apply(final PublicKey key, final String msg) {
		byte[] encrypted = cryptoBase.doCrypto(
				PUBLIC_KEY_CIPHER_ALG, PUBLIC_KEY_PROVIDER, key, Cipher.ENCRYPT_MODE, msg.getBytes());
		return new PublicKeyCiphertext(encrypted);
	}
}
