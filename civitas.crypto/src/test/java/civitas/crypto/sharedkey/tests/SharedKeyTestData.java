package civitas.crypto.sharedkey.tests;

import static org.mockito.Mockito.mock;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import civitas.crypto.sharedkey.SharedKey;

public interface SharedKeyTestData {
	int SHARED_KEY_SIZE = 256;
	String SHARED_KEY_BASE64 = "dGVzdGRhdGE=";
	String SHARED_KEY_NAME = "sharedKey-civitas";
	byte[] SHARED_KEY_BYTES = Base64.getDecoder().decode(SHARED_KEY_BASE64);
	byte[] SHARED_KEY_BYTES_BAD = "bad bytes".getBytes();
	SecretKeySpec SHARED_KEY_SPEC = new SecretKeySpec(SHARED_KEY_BYTES, "AES");
	SecretKeySpec SHARED_KEY_SPEC_BAD = new SecretKeySpec(SHARED_KEY_BYTES_BAD, "AES");
	SecretKey SHARED_KEY_JS = mock(SecretKey.class);

	SharedKey SHARED_KEY = new SharedKey(SHARED_KEY_SPEC, SHARED_KEY_NAME);
	String SHARED_KEY_ON_WIRE = SHARED_KEY_NAME + "\n" + SHARED_KEY_BASE64 + "\n";
}
