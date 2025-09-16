package civitas.common.mix.voteelementrevelation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.common.mix.capabilitymix.CapabilityMixTestData;
import civitas.common.mix.votemix.VoteMixTestData;
import civitas.crypto.publickey.ElGamalPublicKeyTestData;

class VerifyMixVoteElementRevelationTest extends RandomAwareTestBase
		implements MixVoteElementRevelationTestData, ElGamalPublicKeyTestData,
		VoteMixTestData, CapabilityMixTestData {

	@InjectMocks
	VerifyMixVoteElementRevelation verifyMixVoteElementRevelation;

	@Test
	@DisplayName("checks that the target choice and capability are the reencrytions of the source with the given factors ")
	void test() {
		assertTrue(verifyMixVoteElementRevelation.apply(VOTE_ELEMENT_REVELATION,
				EL_GAMAL_PUBLIC_KEY_E, 0, 1, FROM_MIX, TO_MIX));
	}

	// FIXME: turn all verify* into void throwing exception if the verification
	// fails

	@Test
	@DisplayName("if the capability is not reencrypted to target, the verification fails")
	void test1() {
		assertFalse(verifyMixVoteElementRevelation.apply(VOTE_ELEMENT_REVELATION,
				EL_GAMAL_PUBLIC_KEY_E, 0, 0, FROM_MIX, TO_MIX));
	}

	@Test
	@DisplayName("if the choice is not reencrypted to target, the verification fails")
	void test2() {
		assertFalse(verifyMixVoteElementRevelation.apply(VOTE_ELEMENT_REVELATION,
				EL_GAMAL_PUBLIC_KEY_EPRIME, 0, 1, FROM_MIX, TO_MIX));
	}

	@Test
	@DisplayName("if the fromMix is not a VoteMix, the verification fails")
	void test3() {
		assertFalse(verifyMixVoteElementRevelation.apply(VOTE_ELEMENT_REVELATION,
				EL_GAMAL_PUBLIC_KEY_E, 0, 1, CAPABILITY_MIX_MOCK, TO_MIX));
	}

	@Test
	@DisplayName("if the toMix is not a VoteMix, the verification fails")
	void test4() {
		assertFalse(verifyMixVoteElementRevelation.apply(VOTE_ELEMENT_REVELATION,
				EL_GAMAL_PUBLIC_KEY_E, 0, 1, FROM_MIX, CAPABILITY_MIX_MOCK));
	}

	@Test
	@DisplayName("if the revelation is null, a NullPointerException is thrown")
	void test5() {
		assertThrows(NullPointerException.class,
				() -> verifyMixVoteElementRevelation.apply(null, EL_GAMAL_PUBLIC_KEY_E,
						0, 1, FROM_MIX, TO_MIX));
	}

	@Test
	@DisplayName("if the key is null, a NullPointerException is thrown")
	void test6() {
		assertThrows(NullPointerException.class,
				() -> verifyMixVoteElementRevelation.apply(VOTE_ELEMENT_REVELATION,
						null, 0, 1, FROM_MIX, TO_MIX));
	}

	@Test
	@DisplayName("if the fromMix is null, a NullPointerException is thrown")
	void test7() {
		assertThrows(NullPointerException.class,
				() -> verifyMixVoteElementRevelation.apply(VOTE_ELEMENT_REVELATION,
						EL_GAMAL_PUBLIC_KEY_E, 0, 1, null, TO_MIX));
	}

	@Test
	@DisplayName("if the toMix is null, a NullPointerException is thrown")
	void test8() {
		assertThrows(NullPointerException.class,
				() -> verifyMixVoteElementRevelation.apply(VOTE_ELEMENT_REVELATION,
						EL_GAMAL_PUBLIC_KEY_E, 0, 1, FROM_MIX, null));
	}

	@Test
	@DisplayName("if the fromIndex is out of bounds, an IndexOutOfBoundsException is thrown")
	void test9() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> verifyMixVoteElementRevelation.apply(VOTE_ELEMENT_REVELATION,
						EL_GAMAL_PUBLIC_KEY_E, 2, 1, FROM_MIX, TO_MIX));
	}

	@Test
	@DisplayName("if the toIndex is out of bounds, an IndexOutOfBoundsException is thrown")
	void test10() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> verifyMixVoteElementRevelation.apply(VOTE_ELEMENT_REVELATION,
						EL_GAMAL_PUBLIC_KEY_E, 0, 2, FROM_MIX, TO_MIX));
	}

}
