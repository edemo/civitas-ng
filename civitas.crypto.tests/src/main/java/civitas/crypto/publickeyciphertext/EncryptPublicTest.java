package civitas.crypto.publickeyciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.crypto.rsapublickey.PublicKeyTestData;
import civitas.util.BasicValuesTestData;

class EncryptPublicTest extends RandomAwareTestBase implements
		PublicKeyTestData, BasicValuesTestData, PublicKeyCiphertextTestData {
	@InjectMocks
	EncryptPublic encryptPublic;

	@Test
	void test() {
		assertEquals(SOMESTRING_ENCRYPTED,
				encryptPublic.apply(PUBLIC_KEY, SOMESTRING));
	}

}
