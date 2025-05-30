package civitas.crypto.publickey;

import javax.crypto.Cipher;

import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.Constants;
import civitas.crypto.external.DoCrypto;
import civitas.crypto.msg.PublicKeyMsgC;
import civitas.crypto.privatekey.PrivateKey;
import civitas.crypto.privatekey.PrivateKeyC;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertext;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertextC;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.util.Use;

public class DecryptPublic implements Constants {
	@Use
	DoCrypto doCrypto;

	public PublicKeyMsg apply(PrivateKey key, PublicKeyCiphertext ciphertext)
			throws CryptoError {
		PrivateKeyC keyc = (PrivateKeyC) key;
		PublicKeyCiphertextC ciphertextc = (PublicKeyCiphertextC) ciphertext;
		byte[] plaintext = doCrypto.apply(PUBLIC_KEY_CIPHER_ALG,
				PUBLIC_KEY_PROVIDER, keyc.k, Cipher.DECRYPT_MODE,
				ciphertextc.toBytes());
		return new PublicKeyMsgC(plaintext);
	}

}
