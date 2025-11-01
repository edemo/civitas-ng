package civitas.crypto.rsapublickey.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.publickeyciphertext.tests.PublicKeyCiphertextTestData;
import civitas.crypto.rsaprivatekey.tests.PrivateKeyTestData;
import civitas.crypto.rsapublickey.DecryptPublic;
import civitas.util.tests.BasicValuesTestData;

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
