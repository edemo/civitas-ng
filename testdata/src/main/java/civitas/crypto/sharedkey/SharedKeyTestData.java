package civitas.crypto.sharedkey;

import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

public interface SharedKeyTestData {
	public static final String SHARED_KEY_BASE64 = "dGVzdGRhdGE=";
	public static final String SHARED_KEY_NAME = "shared key name";
	SharedKey SHARED_KEY = new SharedKey(
			new SecretKeySpec(Base64.getDecoder().decode(SHARED_KEY_BASE64), "AES"),
			SHARED_KEY_NAME);
	public static final String SHARED_KEY_ON_WIRE = SHARED_KEY_NAME + "\n"
			+ SHARED_KEY_BASE64 + "\n";

}
