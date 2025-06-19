package civitas.crypto.petshare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.common.TestBase;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
public class ConstructPETShareTest extends TestBase
		implements PETShareTestData {

	@Autowired
	ConstructPETShare constructPETShare;

	@Test
	@DisplayName("constructs a pet share with the two ciphertexts and a random element")
	void test() {
		PETShare actual = new PETShare(CIPHERTEXT_E, CIPHERTEXT_EPRIME, RANDOMS_0);
		assertEquals(actual.ciphertext1, CIPHERTEXT_E);
		assertEquals(actual.ciphertext2, CIPHERTEXT_EPRIME);
		assertEquals(actual.exponent, RANDOMS_0);
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
