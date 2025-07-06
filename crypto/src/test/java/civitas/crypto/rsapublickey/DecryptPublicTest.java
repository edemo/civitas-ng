package civitas.crypto.rsapublickey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.BasicValuesTestData;
import civitas.crypto.CryptoError;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertextTestData;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;

class DecryptPublicTest extends TestBase implements PublicKeyCiphertextTestData,
		PrivateKeyTestData, BasicValuesTestData {

	@InjectMocks
	DecryptPublic decryptPublic;

	@Test
	@DisplayName("decrypts a ciphertext using the private key")
	void test() throws UnsupportedEncodingException, CryptoError {
		assertEquals(SOMESTRING,
				decryptPublic.apply(PRIVATE_KEY, SOMESTRING_ENCRYPTED));
	}

}
