package civitas.crypto.publickeyciphertext;

import javax.crypto.Cipher;

import civitas.crypto.Constants;
import civitas.crypto.external.DoCrypto;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.rsapublickey.PublicKey;
import civitas.crypto.rsapublickey.PublicKeyC;
import civitas.util.Use;

public class EncryptPublic implements Constants {
	@Use
	DoCrypto doCrypto;

	public PublicKeyCiphertext apply(PublicKey key, PublicKeyMsg msg) {
		PublicKeyC keyc = (PublicKeyC) key;
		PublicKeyMsg msgc = (PublicKeyMsg) msg;
		byte[] encrypted = doCrypto.apply(PUBLIC_KEY_CIPHER_ALG,
				PUBLIC_KEY_PROVIDER, keyc.k, Cipher.ENCRYPT_MODE, msgc.m.getBytes());
		return new PublicKeyCiphertext(encrypted);
	}

}
