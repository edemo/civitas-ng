package civitas.crypto.algorithms;

import javax.crypto.Cipher;

import civitas.crypto.PublicKey;
import civitas.crypto.PublicKeyCiphertext;
import civitas.crypto.PublicKeyMsg;
import civitas.crypto.concrete.PublicKeyC;
import civitas.crypto.concrete.PublicKeyCiphertextC;
import civitas.crypto.concrete.PublicKeyMsgC;
import civitas.util.Use;

public class EncryptPublic implements Constants {
	@Use
	DoCrypto doCrypto;

	public PublicKeyCiphertext apply(PublicKey key, PublicKeyMsg msg) {
		PublicKeyC keyc = (PublicKeyC) key;
		PublicKeyMsgC msgc = (PublicKeyMsgC) msg;
		byte[] encrypted = doCrypto.apply(PUBLIC_KEY_CIPHER_ALG,
				PUBLIC_KEY_PROVIDER, keyc.k, Cipher.ENCRYPT_MODE, msgc.toBytes());
		return new PublicKeyCiphertextC(encrypted);
	}

}
