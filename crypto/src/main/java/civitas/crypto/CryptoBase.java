package civitas.crypto;

import java.security.KeyFactory;
import java.security.Security;
import java.security.Signature;
import java.util.Random;

import javax.crypto.SecretKeyFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Controller;

import civitas.crypto.messagedigest.MessageDigest;
import civitas.util.CivitasBigInteger;

@Controller
public class CryptoBase implements Constants {
	public SecretKeyFactory sharedKeyFactory;
	public KeyFactory publicKeyFactory;
	public MessageDigest messageDigest;
	public Signature rsaSigner;

	public CryptoBase() {
		BouncyCastleProvider bc = new BouncyCastleProvider();
		Security.addProvider(bc);
		try {
			sharedKeyFactory = SecretKeyFactory.getInstance(SHARED_KEY_ALG,
					SHARED_KEY_PROVIDER);
			publicKeyFactory = KeyFactory.getInstance(PUBLIC_KEY_ALG,
					PUBLIC_KEY_PROVIDER);
			messageDigest = new MessageDigest(
					java.security.MessageDigest.getInstance(MESSAGE_DIGEST_ALG));
			rsaSigner = java.security.Signature.getInstance(PUBLIC_KEY_SIGNATURE_ALG,
					PUBLIC_KEY_PROVIDER);
		} catch (Exception e) {
			throw new CryptoError(e);
		}
	}

	public CivitasBigInteger obtainProbablePrime(int bitLenght, int certainty,
			Random random) {
		return CivitasBigInteger.constructProbablePrime(bitLenght, certainty,
				random);
	}

}
