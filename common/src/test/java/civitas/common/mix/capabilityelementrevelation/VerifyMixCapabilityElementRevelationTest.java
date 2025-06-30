package civitas.common.mix.capabilityelementrevelation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.common.mix.capabilitymix.CapabilityMixTestData;

class VerifyMixCapabilityElementRevelationTest extends TestBase
		implements CapabilityMixTestData {

	@InjectMocks
	VerifyMixCapabilityElementRevelation verifyMixCapabilityElementRevelation;

	@Test
	@DisplayName("checks that the target capability is the reencryption of the source capability"
			+ "using the factor in the relevation and the key")
	void test() {
		assertTrue(verifyMixCapabilityElementRevelation.apply(
				CAPABILITY_ELEMENT_RELEVATION, EL_GAMAL_PUBLIC_KEY_EPRIME, 0, 1,
				CAPABILITY_MIX_CAPABILITY_ADDED, CAPABILITY_MIX_REMIXED));
	}

	@Test
	@DisplayName("if the relevation is null, a NullPointerException is thrown")
	void test1() {
		assertThrows(NullPointerException.class,
				() -> verifyMixCapabilityElementRevelation.apply(null,
						EL_GAMAL_PUBLIC_KEY_EPRIME, 0, 1, CAPABILITY_MIX_CAPABILITY_ADDED,
						CAPABILITY_MIX_REMIXED));
	}

	@Test
	@DisplayName("if the key is null, a NullPointerException is thrown")
	void test2() {
		assertThrows(NullPointerException.class,
				() -> verifyMixCapabilityElementRevelation.apply(
						CAPABILITY_ELEMENT_RELEVATION, null, 0, 1,
						CAPABILITY_MIX_CAPABILITY_ADDED, CAPABILITY_MIX_REMIXED));
	}

	@Test
	@DisplayName("if the source mix is null, a NullPointerException is thrown")
	void test3() {
		assertThrows(NullPointerException.class,
				() -> verifyMixCapabilityElementRevelation.apply(
						CAPABILITY_ELEMENT_RELEVATION, EL_GAMAL_PUBLIC_KEY_EPRIME, 0, 1,
						null, CAPABILITY_MIX_REMIXED));
	}

	@Test
	@DisplayName("if the destination mix is null, a NullPointerException is thrown")
	void test4() {
		assertThrows(NullPointerException.class,
				() -> verifyMixCapabilityElementRevelation.apply(
						CAPABILITY_ELEMENT_RELEVATION, EL_GAMAL_PUBLIC_KEY_EPRIME, 0, 1,
						CAPABILITY_MIX_CAPABILITY_ADDED, null));
	}

	@Test
	@DisplayName("if the source index is bad, an IndexOutOfBoundsException is thrown")
	void test5() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> verifyMixCapabilityElementRevelation.apply(
						CAPABILITY_ELEMENT_RELEVATION, EL_GAMAL_PUBLIC_KEY_EPRIME, 2, 1,
						CAPABILITY_MIX_CAPABILITY_ADDED, CAPABILITY_MIX_REMIXED));
	}

	@Test
	@DisplayName("if the target index is bad, an IndexOutOfBoundsException is thrown")
	void test6() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> verifyMixCapabilityElementRevelation.apply(
						CAPABILITY_ELEMENT_RELEVATION, EL_GAMAL_PUBLIC_KEY_EPRIME, 0, 3,
						CAPABILITY_MIX_CAPABILITY_ADDED, CAPABILITY_MIX_REMIXED));
	}

}
