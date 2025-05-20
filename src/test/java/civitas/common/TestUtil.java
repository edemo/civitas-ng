package civitas.common;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import civitas.crypto.concrete.ConcreteTestData;
import civitas.crypto.concrete.CryptoAlgs;
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
			field = CryptoAlgs.class.getDeclaredField("randomGenerator");
			field.setAccessible(true);

			oldRng = (Random) field.get(CryptoAlgs.class);

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

			field.set(CryptoAlgs.class, RANDOM_GENERATOR_FAKE_SERIES);
		} catch (Exception e) {
			throw new Error(e);
		}

	}

	public static void tearDownFakeRandom() {
		try {
			Field field;
			field = CryptoAlgs.class.getDeclaredField("randomGenerator");
			field.setAccessible(true);

			field.set(CryptoAlgs.class, oldRng);
		} catch (Exception e) {
			throw new Error(e);
		}

	}

}
