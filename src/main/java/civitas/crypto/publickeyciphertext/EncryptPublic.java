package civitas.crypto.publickeyciphertext;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.Constants;
import civitas.crypto.external.DoCrypto;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.rsapublickey.PublicKey;

@Service
public class EncryptPublic implements Constants {
	@Autowired
	DoCrypto doCrypto;

	public PublicKeyCiphertext apply(PublicKey key, PublicKeyMsg msg) {
		PublicKey keyc = key;
		PublicKeyMsg msgc = msg;
		byte[] encrypted = doCrypto.apply(PUBLIC_KEY_CIPHER_ALG,
				PUBLIC_KEY_PROVIDER, keyc.key, Cipher.ENCRYPT_MODE, msgc.m.getBytes());
		return new PublicKeyCiphertext(encrypted);
	}

}
