package civitas.crypto.algorithms;

import javax.crypto.Cipher;

import civitas.crypto.external.DoCrypto;
import civitas.crypto.msg.PublicKeyMsgC;
import civitas.crypto.publickey.PublicKey;
import civitas.crypto.publickey.PublicKeyC;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertext;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertextC;
import civitas.crypto.publickeymsg.PublicKeyMsg;
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
