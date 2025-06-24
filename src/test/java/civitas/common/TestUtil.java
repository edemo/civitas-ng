package civitas.common;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.function.Function;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.mockito.invocation.InvocationOnMock;

import civitas.util.CivitasBigInteger;

public class TestUtil {

	public static void fakeRandomToArray(InvocationOnMock invocation,
			CivitasBigInteger random) {
		byte[] array = invocation.getArgument(0);
		java.util.Arrays.fill(array, (byte) 0);
		byte[] aBytes = random.toByteArray();
		int diff = aBytes.length - array.length;
		for (int i = 0; i < array.length; i++) {
			try {
				array[i] = aBytes[i + diff];
			} catch (ArrayIndexOutOfBoundsException e) {
				throw e;
			}
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

	public static MessageDigest getBaselineDigest() {
		try {
			return java.security.MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new Error();
		}
	}

	static int n = 0;

	public static Function<CivitasBigInteger, CivitasBigInteger> pick(int mod) {
		n = 0;
		return (s) -> {
			if (n < 8 && mod == n++ % 2) {
				return s;
			} else
				return null;
		};
	}

}
