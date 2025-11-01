package civitas.common.mix.revelation.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.mix.capabilitymix.tests.CapabilityMixTestData;
import civitas.common.mix.revelation.VerifyMixRevelation;
import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.ciphertext.tests.ElGamalCiphertextTestData;
import civitas.crypto.publickey.tests.ElGamalPublicKeyTestData;

class VerifyMixRevelationTest extends RandomAwareTestBase
		implements ElGamalPublicKeyTestData, CapabilityMixTestData, ElGamalCiphertextTestData, MixRevelationTestData {

	@InjectMocks
	VerifyMixRevelation verifyMixRevelation;

	@Test
	@DisplayName("verifies the mix revelation. For each revelations\n"
			+ "- checks that the commitment is the hash of the mapping and the nonce"
			+ "- checks that the revelation verifies")
	void test() {

		assertTrue(verifyMixRevelation.apply(
				MIX_REVELATION,
				EL_GAMAL_PUBLIC_KEY_EPRIME,
				CAPABILITY_MIX_INITIAL,
				CAPABILITY_MIX_LEFT,
				CAPABILITY_MIX_RIGHT,
				MIX_REVELATION_DIRECTIONS));
	}

	@Test
	@DisplayName("fails if verifyMixElementRevelation fails")
	void test1() {
		verifyMixRevelation.apply(
				MIX_REVELATION,
				EL_GAMAL_PUBLIC_KEY_E,
				CAPABILITY_MIX_INITIAL,
				CAPABILITY_MIX_LEFT,
				CAPABILITY_MIX_RIGHT,
				MIX_REVELATION_DIRECTIONS);
	}

	@Test
	@DisplayName("fails if the hashes do not match")
	void test2() {
		// hash nonequal
		verifyMixRevelation.apply(
				MIX_REVELATION,
				EL_GAMAL_PUBLIC_KEY_E,
				CAPABILITY_MIX_INITIAL,
				CAPABILITY_MIX_RIGHT,
				CAPABILITY_MIX_LEFT,
				MIX_REVELATION_DIRECTIONS);
	}

	@Test
	@DisplayName("fails if a revelation is null")
	void test3() {
		verifyMixRevelation.apply(
				MIX_REVELATION_WITH_NULL_REVELATION,
				EL_GAMAL_PUBLIC_KEY_E,
				CAPABILITY_MIX_INITIAL,
				CAPABILITY_MIX_RIGHT,
				CAPABILITY_MIX_LEFT,
				MIX_REVELATION_DIRECTIONS);
	}

	@Test
	@DisplayName("fails if the indicator in a revelation does not match the corresponding direction")
	void test4() {
		verifyMixRevelation.apply(
				MIX_REVELATION,
				EL_GAMAL_PUBLIC_KEY_E,
				CAPABILITY_MIX_INITIAL,
				CAPABILITY_MIX_RIGHT,
				CAPABILITY_MIX_LEFT,
				MIX_REVELATION_DIRECTIONS_BAD);
	}

	@Test
	@DisplayName("fails if the length of indicators in the revelation does not match the length of relevations there")
	void test5() {
		verifyMixRevelation.apply(
				MIX_REVELATION_WITH_BAD_INDICATOR_LENGTH,
				EL_GAMAL_PUBLIC_KEY_EPRIME,
				CAPABILITY_MIX_INITIAL,
				CAPABILITY_MIX_LEFT,
				CAPABILITY_MIX_RIGHT,
				MIX_REVELATION_DIRECTIONS);
	}

	@Test
	@DisplayName("fails if the length of indicators in the revelation does not match the length of directions")
	void test6() {
		verifyMixRevelation.apply(
				MIX_REVELATION,
				EL_GAMAL_PUBLIC_KEY_EPRIME,
				CAPABILITY_MIX_INITIAL,
				CAPABILITY_MIX_LEFT,
				CAPABILITY_MIX_RIGHT,
				MIX_REVELATION_DIRECTIONS_SHORT);
	}

	@Test
	@DisplayName("fails if directions is null")
	void test7() {
		verifyMixRevelation.apply(
				MIX_REVELATION,
				EL_GAMAL_PUBLIC_KEY_EPRIME,
				CAPABILITY_MIX_INITIAL,
				CAPABILITY_MIX_LEFT,
				CAPABILITY_MIX_RIGHT,
				null);
	}
}
