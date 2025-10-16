package civitas.crypto.rsaprivatekey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.crypto.CryptoError;

class CreatePrivateKeyFromBytesTest extends RandomAwareTestBase implements PrivateKeyTestData {

	@InjectMocks
	CreatePrivateKeyFromBytes createPrivateKeyFromBytes;

	@Test
	@DisplayName("creates a private key from the bytes representing it")
	void test() {
		assertEquals(
				PRIVATE_KEY, createPrivateKeyFromBytes.apply(Base64.getDecoder().decode(PRIVATE_KEY_BASE64)));
	}

	@Test
	@DisplayName("when the key specification is bad, a CryptoError is thrown")
	void test1() {
		assertThrows(
				CryptoError.class,
				() -> createPrivateKeyFromBytes.apply(Base64.getDecoder().decode(PRIVATE_KEY2_BASE64)));
	}
}
