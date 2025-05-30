package civitas.crypto.rsapublickey;

import javax.crypto.Cipher;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.crypto.external.DoCrypto;
import civitas.crypto.msg.PublicKeyMsgC;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertext;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertext;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.rsaprivatekey.PrivateKey;
import civitas.crypto.rsaprivatekey.PrivateKeyC;
import civitas.util.Use;

public class DecryptPublic implements Constants {
	@Use
	DoCrypto doCrypto;

	public PublicKeyMsg apply(PrivateKey key, PublicKeyCiphertext ciphertext)
			throws CryptoError {
		PrivateKeyC keyc = (PrivateKeyC) key;
		PublicKeyCiphertext ciphertextc = (PublicKeyCiphertext) ciphertext;
		byte[] plaintext = doCrypto.apply(PUBLIC_KEY_CIPHER_ALG,
				PUBLIC_KEY_PROVIDER, keyc.k, Cipher.DECRYPT_MODE,
				ciphertextc.encryptedBytes);
		return new PublicKeyMsgC(plaintext);
	}

}
