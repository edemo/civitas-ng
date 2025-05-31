package civitas.crypto.rsapublickey;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.external.DoCrypto;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertext;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.rsaprivatekey.PrivateKey;
import civitas.crypto.rsaprivatekey.PrivateKeyC;
import civitas.util.Use;

public class DecryptPublic implements Constants {
	@Use
	DoCrypto doCrypto;

	public PublicKeyMsg apply(PrivateKey key, PublicKeyCiphertext ciphertext)
			throws CryptoError, UnsupportedEncodingException {
		PrivateKeyC keyc = (PrivateKeyC) key;
		PublicKeyCiphertext ciphertextc = ciphertext;
		byte[] plaintext = doCrypto.apply(PUBLIC_KEY_CIPHER_ALG,
				PUBLIC_KEY_PROVIDER, keyc.k, Cipher.DECRYPT_MODE,
				ciphertextc.encryptedBytes);
		return new PublicKeyMsg(new String(plaintext, Constants.CHARSET_NAME));
	}

}
