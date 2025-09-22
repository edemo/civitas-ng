package civitas.crypto.rsapublickey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;

class ConvertStringToPublicKeyTest extends RandomAwareTestBase
		implements PublicKeyTestData {

	@InjectMocks
	ConvertStringToPublicKey convertStringToPublicKey;

	@Test
	@DisplayName("converts the base64 string representation of the key data to a PublicKey")
	void test() throws CryptoException {
		assertEquals(PUBLIC_KEY, convertStringToPublicKey.apply(PUBLIC_KEY_BASE64));
	}

	@Test
	@DisplayName("inalid key material results in CryptoException")
	void test1() throws CryptoException {
		assertThrows(CryptoException.class,
				() -> convertStringToPublicKey.apply(PUBLIC_KEY_BAD_BASE64));
	}

}
