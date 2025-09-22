package civitas.crypto.rsapublickey;

import java.security.PrivateKey;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertext;

@Controller
public class DecryptPublic implements Constants {
	@Autowired
	CryptoBase cryptoBase;

	public String apply(final PrivateKey key, final PublicKeyCiphertext ciphertext) {
		byte[] plaintext = cryptoBase.doCrypto(
				PUBLIC_KEY_CIPHER_ALG, PUBLIC_KEY_PROVIDER, key, Cipher.DECRYPT_MODE, ciphertext.encryptedBytes);
		return new String(plaintext, CHARSET);
	}
}
