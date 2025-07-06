package civitas.crypto.sharedkey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.CryptoError;
import civitas.crypto.sharedkeyciphertext.SharedKeyCiphertextTestData;
import civitas.crypto.sharedkeyciphertext.SharedKeyMsgTestData;

class DecryptSharedTest extends TestBase implements SharedKeyTestData,
		SharedKeyCiphertextTestData, SharedKeyMsgTestData {
	@InjectMocks
	DecryptShared decryptShared;

	@Test
	@DisplayName("decrypts a shared key cyphertext")
	void test() throws UnsupportedEncodingException, CryptoError {
		assertEquals(SHARED_KEY_MSG,
				decryptShared.apply(SHARED_KEY, SHARED_KEY_CIPHERTEXT));
	}

}
