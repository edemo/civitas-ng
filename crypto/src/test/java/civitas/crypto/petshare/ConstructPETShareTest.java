package civitas.crypto.petshare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

public class ConstructPETShareTest extends TestBase
		implements PETShareTestData {

	@InjectMocks
	ConstructPETShare constructPETShare;

	@Test
	@DisplayName("constructs a pet share with the two ciphertexts and a random element")
	void test() {
		PETShare actual = constructPETShare.apply(EL_GAMAL_PARAMETERS, CIPHERTEXT_E,
				CIPHERTEXT_EPRIME);
		assertEquals(actual, PET_SHARE_RANDOMS0);
	}

	@Test
	@DisplayName("returns null if ciphertext a is null")
	void test2() {
		assertEquals(null,
				constructPETShare.apply(EL_GAMAL_PARAMETERS, null, CIPHERTEXT_EPRIME));
	}

	@Test
	@DisplayName("returns null if ciphertext b is null")
	void test3() {
		assertEquals(null,
				constructPETShare.apply(EL_GAMAL_PARAMETERS, CIPHERTEXT_E, null));
	}

	@Test
	@DisplayName("returns null if parameters is null")
	void test4() {
		assertEquals(null,
				constructPETShare.apply(null, CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

}
