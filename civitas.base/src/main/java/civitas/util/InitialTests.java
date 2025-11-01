package civitas.util;

import java.nio.charset.StandardCharsets;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import io.github.magwas.konveyor.annotations.Glue;

@Glue
public class InitialTests {

	static {
		try {
			doTests();
		} catch (Exception e) {
			throw new SecurityException("self- and external entity test failed:", e);
		}
	}

	public static void doTests() {
		testUTF8Support();
		checkBouncyCastle();
	}

	public static byte[] testUTF8Support() {
		return "árvíztűrő tükörfúrógép".getBytes(StandardCharsets.UTF_8);
	}

	public static void checkBouncyCastle() {
		String info = new BouncyCastleProvider().getInfo();
		if (!"BouncyCastle Security Provider v1.82".equals(info)) {
			throw new SecurityException("unexpected crypto provider: " + info);
		}
	}
}
