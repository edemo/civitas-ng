package civitas.crypto.sharedkey.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.sharedkey.DecryptShared;
import civitas.crypto.sharedkeyciphertext.tests.SharedKeyCiphertextTestData;
import civitas.crypto.sharedkeyciphertext.tests.SharedKeyMsgTestData;

class DecryptSharedTest extends RandomAwareTestBase
		implements SharedKeyTestData, SharedKeyCiphertextTestData, SharedKeyMsgTestData {
	@InjectMocks
	DecryptShared decryptShared;

	@Test
	@DisplayName("decrypts a shared key cyphertext")
	void test() {
		assertEquals(SHARED_KEY_MSG, decryptShared.apply(SHARED_KEY, SHARED_KEY_CIPHERTEXT));
	}
}
