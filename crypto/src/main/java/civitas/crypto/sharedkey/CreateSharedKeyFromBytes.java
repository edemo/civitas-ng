package civitas.crypto.sharedkey;

import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoBase;
import civitas.crypto.CryptoError;

@Controller
public class CreateSharedKeyFromBytes implements Constants {

	@Autowired
	CryptoBase cryptoBase;

	public SecretKey apply(final byte[] bs) {
		SecretKeySpec skeySpec = new SecretKeySpec(bs, SHARED_KEY_ALG);
		try {
			return cryptoBase.sharedKeyFactory.generateSecret(skeySpec);
		} catch (InvalidKeySpecException e) {
			throw new CryptoError(e);
		}
	}

}
