package civitas.crypto.algorithms.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import civitas.crypto.algorithms.CreateFreshNonce;
import civitas.crypto.algorithms.CreateFreshNonceBase64;
import civitas.util.tests.BasicValuesTestData;
import io.github.magwas.konveyor.testing.TestBase;

class CreateFreshNonceBase64Test extends TestBase implements BasicValuesTestData {

	@InjectMocks
	CreateFreshNonceBase64 createFreshNonceBase64;

	@Mock
	CreateFreshNonce createFreshNonce;

	@Test
	@DisplayName("creates bitlenght/8 random bytes")
	void test() {

		assertEquals(BYTELENGTH, Base64.getDecoder().decode(createFreshNonceBase64.apply(BITLENGTH)).length);
	}

	@Test
	@DisplayName("if bitlength is not divisible by 8, the number of bytes is rounded up")
	void test2() {
		assertEquals(BYTELENGTH, Base64.getDecoder().decode(createFreshNonceBase64.apply(BITLENGTH - 1)).length);
	}

	@Test
	@DisplayName("uses createFreshNonce (so the right random generator)")
	void test1() {
		createFreshNonceBase64.apply(BITLENGTH);
		verify(createFreshNonce).apply(BITLENGTH);
	}
}
