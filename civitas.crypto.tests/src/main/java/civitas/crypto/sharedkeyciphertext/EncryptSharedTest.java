package civitas.crypto.sharedkeyciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.sharedkey.SharedKeyTestData;
import io.github.magwas.testing.TestBase;

class EncryptSharedTest extends TestBase
		implements SharedKeyTestData, SharedKeyMsgTestData, SharedKeyCiphertextTestData {

	@InjectMocks
	EncryptShared encryptShared;

	@Test
	@DisplayName("encrypts a string with a shared key")
	void test() {
		assertEquals(SHARED_KEY_CIPHERTEXT, encryptShared.apply(SHARED_KEY, SHARED_KEY_MSG));
	}
}
