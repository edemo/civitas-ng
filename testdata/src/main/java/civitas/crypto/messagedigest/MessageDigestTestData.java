package civitas.crypto.messagedigest;

import java.security.MessageDigest;

import civitas.crypto.BasicValuesTestData;

public interface MessageDigestTestData extends BasicValuesTestData {
	public static final MessageDigest BASELINE_DIGEST = TestUtil
			.getBaselineDigest();

}
