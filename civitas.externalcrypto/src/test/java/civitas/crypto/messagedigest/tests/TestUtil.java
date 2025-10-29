package civitas.crypto.messagedigest.tests;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestUtil {
	public static MessageDigest getBaselineDigest() {
		try {
			return MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new Error();
		}
	}
}
