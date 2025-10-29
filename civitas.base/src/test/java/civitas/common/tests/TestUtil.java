package civitas.common.tests;

import java.util.function.Function;

import org.mockito.invocation.InvocationOnMock;

import civitas.util.CivitasBigInteger;

public class TestUtil {

	static int n = 0;

	public static void fakeRandomToArray(final InvocationOnMock invocation, final CivitasBigInteger random) {
		byte[] array = invocation.getArgument(0);
		java.util.Arrays.fill(array, (byte) 0);
		byte[] aBytes = random.toByteArray();
		int diff = aBytes.length - array.length;
		for (int i = 0; i < array.length; i++) {
			array[i] = aBytes[i + diff];
		}
	}

	public static <T> T construct(final Class<T> klass, final Object... params) {
		Class<?>[] types = new Class<?>[params.length];

		for (int i = 0; i < types.length; i++) {
			Class<?> cl = params[i].getClass();
			types[i] = cl;
		}
		try {
			return klass.getDeclaredConstructor(types).newInstance(params);
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public static Function<CivitasBigInteger, CivitasBigInteger> pick(final int mod) {
		n = 0;
		return s -> {
			if (n < 8 && mod == n++ % 2) {
				return s;
			} else {
				return null;
			}
		};
	}
}
