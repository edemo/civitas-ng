package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.BasicValuesTestData;

public class CreateFreshNonceTest extends TestBase
		implements BasicValuesTestData {

	@InjectMocks
	CreateFreshNonce createFreshNonce;

	@Test
	@DisplayName("creates bitlenght/8 random bytes")
	void test() {
		byte[] byteArray = RANDOMS_0.toByteArray();
		assertArrayEquals(Arrays.copyOfRange(byteArray,
				byteArray.length - BYTELENGTH, byteArray.length),
				createFreshNonce.apply(BITLENGTH));
	}

	@Test
	@DisplayName("if bitlength is not divisible by 8, the number of bytes is rounded up")
	void test2() {
		assertEquals(BYTELENGTH, createFreshNonce.apply(BITLENGTH - 1).length);
	}

	@Test
	@DisplayName("uses the right random generator")
	void test1() {
		createFreshNonce.apply(BITLENGTH);
		verify(createFreshNonce.getRandomGenerator).apply();
	}

}
