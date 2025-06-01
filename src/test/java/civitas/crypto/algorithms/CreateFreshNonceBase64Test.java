package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.BasicValuesTestData;
import civitas.util.Tested;

public class CreateFreshNonceBase64Test extends TestBase
		implements BasicValuesTestData {

	@Tested
	CreateFreshNonceBase64 createFreshNonceBase64;

	@Test
	@DisplayName("creates bitlenght/8 random bytes")
	void test() {

		assertEquals(BYTELENGTH, Base64.getDecoder()
				.decode(createFreshNonceBase64.apply(BITLENGTH)).length);
	}

	@Test
	@DisplayName("if bitlength is not divisible by 8, the number of bytes is rounded up")
	void test2() {
		assertEquals(BYTELENGTH, Base64.getDecoder()
				.decode(createFreshNonceBase64.apply(BITLENGTH - 1)).length);
	}

	@Test
	@DisplayName("uses createFreshNonce (so the right random generator)")
	void test1() {
		createFreshNonceBase64.apply(BITLENGTH);
		verify(createFreshNonceBase64.createFreshNonce).apply(BITLENGTH);
	}

}
