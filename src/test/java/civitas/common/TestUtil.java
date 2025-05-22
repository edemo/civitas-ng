package civitas.common;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import civitas.crypto.algorithms.Constants;
import civitas.crypto.concrete.ConcreteTestData;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalProof1OfLC;
import civitas.util.CivitasBigInteger;

public class TestUtil {

	public static String readerToString(StringReader r) throws IOException {
		char[] target = new char[40];
		int i = r.read(target);
		String actual = new String(Arrays.copyOfRange(target, 0, i));
		return actual;
	}

	public static void fakeRandomToArray(InvocationOnMock invocation,
			CivitasBigInteger random) {
		byte[] array = invocation.getArgument(0);
		java.util.Arrays.fill(array, (byte) 0);
		byte[] aBytes = random.toByteArray();
		for (int i = array.length - aBytes.length; i < array.length; i++) {
			array[i] = aBytes[i - (array.length - aBytes.length)];
		}
	}

	static int step;
	static Random oldRng;

	public static void setUpFakeRandom() {
		try {
			Field field;
			field = Constants.class.getDeclaredField("RANDOM_GENERATOR");
			field.setAccessible(true);

			oldRng = (Random) field.get(Constants.class);

			SecureRandom RANDOM_GENERATOR_FAKE_SERIES = mock(SecureRandom.class,
					withSettings().withoutAnnotations());
			step = 0;
			doAnswer(new Answer<Void>() {

				@Override
				public Void answer(InvocationOnMock invocation) {
					TestUtil.fakeRandomToArray(invocation,
							ConcreteTestData.RANDOMS.get(step));
					step++;
					return null;
				}
			}).when(RANDOM_GENERATOR_FAKE_SERIES).nextBytes(any());

			field.set(Constants.class, RANDOM_GENERATOR_FAKE_SERIES);
		} catch (Exception e) {
			throw new Error(e);
		}

	}

	public static void tearDownFakeRandom() {
		try {
			Field field;
			field = Constants.class.getDeclaredField("RANDOM_GENERATOR");
			field.setAccessible(true);

			field.set(Constants.class, oldRng);
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public static <T> T construct(Class<T> klass, Object... params) {
		Class<? extends Object>[] types = new Class<?>[params.length];

		for (int i = 0; i < types.length; i++) {
			Class<? extends Object> cl = params[i].getClass();
			types[i] = cl;
		}
		try {
			return klass.getDeclaredConstructor(types).newInstance(params);
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public static final KeyFactory keyFactory;

	static {
		Security.addProvider(new BouncyCastleProvider());
		try {
			keyFactory = KeyFactory.getInstance("RSA", "BC");
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public static PrivateKey generatePrivate(String privateKey2Base64) {
		try {
			return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(
					Base64.getDecoder().decode(privateKey2Base64)));
		} catch (InvalidKeySpecException e) {
			throw new Error(e);
		}
	}

	public static PublicKey generatePublic(String privateKey2Base64) {
		try {
			return keyFactory.generatePublic(new X509EncodedKeySpec(
					Base64.getDecoder().decode(privateKey2Base64)));
		} catch (InvalidKeySpecException e) {
			throw new Error(e);
		}
	}

	public static MessageDigest getBaselineDigest() {
		try {
			return java.security.MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new Error();
		}
	}

	public static CivitasBigInteger asBigint(String s) {
		return new CivitasBigInteger(Base64.getDecoder().decode(s));
	}

	public static String fromBigInt(CivitasBigInteger a) {
		return Base64.getEncoder().encodeToString(a.toByteArray());
	}

	public static ElGamalProof1OfLC elGamalProof1OfLCFromXML(String aa) {
		try {
			return ElGamalProof1OfLC.fromXML(new StringReader(aa));
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public static ElGamalCiphertextC ElGamalCiphertextCfromXML(String a) {
		try {
			return ElGamalCiphertextC.fromXML(new StringReader(a));
		} catch (Exception e) {
			throw new Error(e);
		}
	}
}
