package civitas.crypto;

import java.security.KeyFactory;
import java.security.Security;

import javax.crypto.SecretKeyFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Controller;

import civitas.crypto.messagedigest.MessageDigest;

@Controller
public class CryptoBase implements Constants {
	public SecretKeyFactory sharedKeyFactory;
	public KeyFactory publicKeyFactory;
	public MessageDigest messageDigest;

	CryptoBase() {
		BouncyCastleProvider bc = new BouncyCastleProvider();
		Security.addProvider(bc);
		try {
			sharedKeyFactory = SecretKeyFactory.getInstance(SHARED_KEY_ALG,
					SHARED_KEY_PROVIDER);
			publicKeyFactory = KeyFactory.getInstance(PUBLIC_KEY_ALG,
					PUBLIC_KEY_PROVIDER);
			messageDigest = new MessageDigest(
					java.security.MessageDigest.getInstance(MESSAGE_DIGEST_ALG));

		} catch (Exception e) {
			throw new CryptoError(e);
		}
	}

}
