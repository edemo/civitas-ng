package civitas.crypto.rsapublickey;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.external.DoCrypto;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertext;

@Controller
public class DecryptPublic implements Constants {
	@Autowired
	DoCrypto doCrypto;

	public String apply(PrivateKey key, PublicKeyCiphertext ciphertext)
			throws CryptoError, UnsupportedEncodingException {
		PublicKeyCiphertext ciphertextc = ciphertext;
		byte[] plaintext = doCrypto.apply(PUBLIC_KEY_CIPHER_ALG,
				PUBLIC_KEY_PROVIDER, key, Cipher.DECRYPT_MODE,
				ciphertextc.encryptedBytes);
		return new String(plaintext, Constants.CHARSET_NAME);
	}

}
