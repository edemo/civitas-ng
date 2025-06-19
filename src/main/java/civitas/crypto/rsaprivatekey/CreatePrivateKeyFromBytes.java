package civitas.crypto.rsaprivatekey;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.CryptoBase;
import civitas.crypto.CryptoError;

@Service
public class CreatePrivateKeyFromBytes {

	@Autowired
	CryptoBase cryptoBase;

	public java.security.PrivateKey apply(byte[] bs) throws CryptoError {
		KeySpec keySpec = new PKCS8EncodedKeySpec(bs);
		try {
			return cryptoBase.publicKeyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			throw new CryptoError(e);
		}
	}

}
