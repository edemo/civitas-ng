package civitas.crypto.rsapublickey;

import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.bouncycastle.crypto.CryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.CryptoBase;

@Service
public class ConvertStringToPublicKey {

	@Autowired
	CryptoBase cryptoBase;

	public PublicKey apply(String publicK) throws CryptoException {
		byte[] publicBytes = Base64.getDecoder().decode(publicK);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
		try {
			return cryptoBase.publicKeyFactory.generatePublic(keySpec);
		} catch (InvalidKeySpecException e) {
			throw new CryptoException("cannot convert", e);
		}
	}
}
