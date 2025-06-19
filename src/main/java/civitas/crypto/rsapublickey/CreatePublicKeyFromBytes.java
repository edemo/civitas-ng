package civitas.crypto.rsapublickey;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.CryptoBase;
import civitas.crypto.CryptoError;

@Service
public class CreatePublicKeyFromBytes {

	@Autowired
	CryptoBase cryptoBase;

	public java.security.PublicKey apply(byte[] bs) throws CryptoError {
		KeySpec keySpec = new X509EncodedKeySpec(bs);
		try {
			return cryptoBase.publicKeyFactory.generatePublic(keySpec);
		} catch (InvalidKeySpecException e) {
			throw new CryptoError(e);
		}
	}

}
