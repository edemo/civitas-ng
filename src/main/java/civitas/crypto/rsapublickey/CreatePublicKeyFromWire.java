package civitas.crypto.rsapublickey;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.CryptoBase;
import civitas.crypto.CryptoError;
import civitas.util.KeyOnWire;

@Service
public class CreatePublicKeyFromWire {

	@Autowired
	CryptoBase cryptoBase;

	public PublicKey apply(KeyOnWire onWire) throws CryptoError {
		KeySpec keySpec = new X509EncodedKeySpec(
				Base64.getDecoder().decode(onWire.keyBase64));
		try {
			return new PublicKey(cryptoBase.publicKeyFactory.generatePublic(keySpec));
		} catch (InvalidKeySpecException e) {
			throw new CryptoError(e);
		}
	}

}
