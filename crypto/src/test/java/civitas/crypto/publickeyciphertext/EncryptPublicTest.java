package civitas.crypto.publickeyciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.BasicValuesTestData;
import civitas.crypto.rsapublickey.PublicKeyTestData;

class EncryptPublicTest extends TestBase implements PublicKeyTestData,
		BasicValuesTestData, PublicKeyCiphertextTestData {
	@InjectMocks
	EncryptPublic encryptPublic;

	@Test
	void test() {
		assertEquals(SOMESTRING_ENCRYPTED,
				encryptPublic.apply(PUBLIC_KEY, SOMESTRING));
	}

}
