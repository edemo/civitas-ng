package civitas.util;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

@Boilerplate
public class InitialTests {

	static {

		try {
			doTests();
		} catch (Exception e) {
			throw new SecurityException("self- and external entity test failed:", e);
		}
	}

	public static void doTests() throws UnsupportedEncodingException {
		testUTF8Support();
		checkBouncyCastle();
	}

	public static void testUTF8Support() throws UnsupportedEncodingException {
		"árvíztűrő tükörfúrógép".getBytes("UTF-8");
	}

	public static void checkBouncyCastle() {
		String info = new BouncyCastleProvider().getInfo();
		if (!info.equals("BouncyCastle Security Provider v1.80"))
			throw new SecurityException("unexpected crypto provider: " + info);
	}
}
