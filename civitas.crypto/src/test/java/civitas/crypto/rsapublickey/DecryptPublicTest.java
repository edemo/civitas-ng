package civitas.crypto.rsapublickey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertextTestData;
import civitas.crypto.rsaprivatekey.PrivateKeyTestData;
import civitas.util.BasicValuesTestData;

class DecryptPublicTest extends RandomAwareTestBase
		implements PublicKeyCiphertextTestData, PrivateKeyTestData, BasicValuesTestData {

	@InjectMocks
	DecryptPublic decryptPublic;

	@Test
	@DisplayName("decrypts a ciphertext using the private key")
	void test() {
		assertEquals(SOMESTRING, decryptPublic.apply(PRIVATE_KEY, SOMESTRING_ENCRYPTED));
	}
}
