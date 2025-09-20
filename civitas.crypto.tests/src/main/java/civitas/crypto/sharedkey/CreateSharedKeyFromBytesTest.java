package civitas.crypto.sharedkey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.crypto.CryptoError;

class CreateSharedKeyFromBytesTest extends RandomAwareTestBase implements SharedKeyTestData {
	@InjectMocks
	CreateSharedKeyFromBytes createSharedKeyFromBytes;

	@Test
	@DisplayName("creates a shared key from the bytes")
	void test() {
		assertEquals(SHARED_KEY_JS, createSharedKeyFromBytes.apply(SHARED_KEY_BYTES));
	}

	@Test
	@DisplayName("an invalid key specification throws CryptoError")
	void test1() {
		assertThrows(CryptoError.class, () -> createSharedKeyFromBytes.apply(SHARED_KEY_BYTES_BAD));
	}
}
