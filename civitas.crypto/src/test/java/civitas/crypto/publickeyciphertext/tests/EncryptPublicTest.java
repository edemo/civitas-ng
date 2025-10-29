package civitas.crypto.publickeyciphertext.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.publickeyciphertext.EncryptPublic;
import civitas.crypto.rsapublickey.tests.PublicKeyTestData;
import civitas.util.tests.BasicValuesTestData;

class EncryptPublicTest extends RandomAwareTestBase
		implements PublicKeyTestData, BasicValuesTestData, PublicKeyCiphertextTestData {
	@InjectMocks
	EncryptPublic encryptPublic;

	@Test
	void test() {
		assertEquals(SOMESTRING_ENCRYPTED, encryptPublic.apply(PUBLIC_KEY, SOMESTRING));
	}
}
