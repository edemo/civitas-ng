package civitas.crypto.petshare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;

class ConstructPETShareTest extends RandomAwareTestBase
		implements PETShareTestData {

	@InjectMocks
	ConstructPETShare constructPETShare;

	@Test
	@DisplayName("constructs a pet share with the two ciphertexts and a random element")
	void test() {
		PETShare actual = constructPETShare.apply(EL_GAMAL_PARAMETERS, CIPHERTEXT_E,
				CIPHERTEXT_EPRIME);
		assertEquals(PET_SHARE_RANDOMS0, actual);
	}

	@Test
	@DisplayName("returns null if ciphertext a is null")
	void test2() {
        assertNull(constructPETShare.apply(EL_GAMAL_PARAMETERS, null, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("returns null if ciphertext b is null")
	void test3() {
        assertNull(constructPETShare.apply(EL_GAMAL_PARAMETERS, CIPHERTEXT_E, null));
	}

	@Test
	@DisplayName("returns null if parameters is null")
	void test4() {
        assertNull(constructPETShare.apply(null, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

}
