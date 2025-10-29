package civitas.crypto.algorithms.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.CryptoBase;
import civitas.crypto.algorithms.CreateFreshNonce;
import civitas.util.tests.BasicValuesTestData;
import io.github.magwas.konveyor.testing.TestBase;
import io.github.magwas.konveyor.testing.TestUtil;

class CreateFreshNonceTest extends TestBase implements BasicValuesTestData {

	@InjectMocks
	CreateFreshNonce createFreshNonce;

	@Test
	@DisplayName("creates bitlenght/8 random bytes")
	void test() {
		assertEquals(BYTELENGTH, createFreshNonce.apply(BITLENGTH).length);
	}

	@Test
	@DisplayName("if bitlength is not divisible by 8, the number of bytes is rounded up")
	void test2() {
		assertEquals(BYTELENGTH, createFreshNonce.apply(BITLENGTH - 1).length);
	}

	@Test
	@DisplayName("uses the right random generator")
	void test1() throws IllegalAccessException {
		createFreshNonce.apply(BITLENGTH);
		verify(TestUtil.dependency(createFreshNonce, CryptoBase.class)).nextBytes(any());
	}
}
