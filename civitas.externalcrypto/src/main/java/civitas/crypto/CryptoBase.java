package civitas.crypto;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.Signature;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;

@Service
public class CryptoBase implements Constants {
	public SecretKeyFactory sharedKeyFactory;
	public KeyFactory publicKeyFactory;
	public Signature rsaSigner;
	private final GetPublicKeyGenerator getPublicKeyGenerator;
	private final GetSharedKeyGenerator getSharedKeyGenerator;

	public CryptoBase() {
		BouncyCastleProvider bc = new BouncyCastleProvider();
		Security.addProvider(bc);
		try {
			getPublicKeyGenerator = new GetPublicKeyGenerator();
			getSharedKeyGenerator = new GetSharedKeyGenerator();
			sharedKeyFactory = SecretKeyFactory.getInstance(SHARED_KEY_ALG,
					SHARED_KEY_PROVIDER);
			publicKeyFactory = KeyFactory.getInstance(PUBLIC_KEY_ALG,
					PUBLIC_KEY_PROVIDER);
			rsaSigner = Signature.getInstance(PUBLIC_KEY_SIGNATURE_ALG,
					PUBLIC_KEY_PROVIDER);
		} catch (Exception e) {
			throw new CryptoError(e);
		}
	}

	public CivitasBigInteger obtainProbablePrime(int bitLenght) {
		return CivitasBigIntegerFactory
				.obtain(new BigInteger(bitLenght, CERTAINTY, RANDOM));
	}

	public CivitasBigInteger generateRandomElement(CivitasBigInteger n) {
		CivitasBigInteger r = null;
		do {
			r = CivitasBigIntegerFactory.obtain(n.bitLength(), RANDOM);
		} while (r.equals(Constants.ZERO) || r.compareTo(n) >= 0);
		return r;
	}

	public Random getRandomGenerator() {
		return RANDOM;
	}

	public byte[] doCrypto(String alg, String provider, Key skey, int mode,
			byte[] input) {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(alg, provider);
			cipher.init(mode, skey);
			return cipher.doFinal(input);
		} catch (Exception e) {
			throw new CryptoError("cannot do crypto", e);
		}
	}

	public void nextBytes(byte[] bs) {
		RANDOM.nextBytes(bs);
	}

	public KeyPairGenerator getPublicKeyGenerator(int keysize) {
		return getPublicKeyGenerator.apply(keysize);
	}

	public KeyGenerator getSharedKeyGenerator(int sharedKeySize) {
		return getSharedKeyGenerator.apply(sharedKeySize);
	}
}
