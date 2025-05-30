package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.BasicValuesTestData;
import civitas.crypto.ConcreteTestBase;
import civitas.util.Tested;

public class CreateFreshNonceTest extends ConcreteTestBase
		implements BasicValuesTestData {

	@Tested
	CreateFreshNonce createFreshNonce;

	@Test
	@DisplayName("creates bitlenght/8 random bytes")
	void test() {
		assertEquals(BYTELENGTH, createFreshNonce.apply(BITLENGTH).length);
	}

	@Test
	@DisplayName("if bitlength is not divisible by 8, the number of bytes is rounded up")
	void test2() {
		assertEquals(8, createFreshNonce.apply(BITLENGTH - 1).length);
	}
}
