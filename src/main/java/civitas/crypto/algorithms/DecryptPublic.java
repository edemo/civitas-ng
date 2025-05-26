package civitas.crypto.algorithms;

import javax.crypto.Cipher;

import civitas.crypto.CryptoError;
import civitas.crypto.PrivateKey;
import civitas.crypto.PublicKeyCiphertext;
import civitas.crypto.PublicKeyMsg;
import civitas.crypto.concrete.PrivateKeyC;
import civitas.crypto.concrete.PublicKeyCiphertextC;
import civitas.crypto.concrete.PublicKeyMsgC;
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
