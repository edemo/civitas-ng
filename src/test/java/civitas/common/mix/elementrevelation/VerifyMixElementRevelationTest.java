package civitas.common.mix.elementrevelation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.common.mix.capabilitymix.CapabilityMixTestData;
import civitas.common.mix.capabilitymixrevelation.MixCapabilityElementRevelationTestData;
import civitas.common.mix.voteelementrevelation.MixVoteElementRevelationTestData;
import civitas.common.mix.votemix.VoteMixTestData;
import civitas.crypto.publickey.ElGamalPublicKeyTestData;

class VerifyMixElementRevelationTest extends TestBase
		implements ElGamalPublicKeyTestData, MixCapabilityElementRevelationTestData,
		CapabilityMixTestData, MixVoteElementRevelationTestData, VoteMixTestData,
		MixElementRevelationTestData {

	@InjectMocks
	VerifyMixElementRevelation verifyMixElementRevelation;

	@Test
	@DisplayName("If the revelation is about capability, uses verifyMixCapabilityElementRevelation")
	void test() {
		verifyMixElementRevelation.apply(CAPABILITY_ELEMENT_RELEVATION_MOCK,
				EL_GAMAL_PUBLIC_KEY_E, 0, 1, CAPABILITY_MIX_MOCK, CAPABILITY_MIX_MOCK);
		verify(verifyMixElementRevelation.verifyMixCapabilityElementRevelation)
				.apply(CAPABILITY_ELEMENT_RELEVATION_MOCK, EL_GAMAL_PUBLIC_KEY_E, 0, 1,
						CAPABILITY_MIX_MOCK, CAPABILITY_MIX_MOCK);
	}

	@Test
	@DisplayName("If the revelation is about vote, uses verifyMixVoteElementRevelation")
	void test1() {
		verifyMixElementRevelation.apply(VOTE_ELEMENT_REVELATION_MOCK,
				EL_GAMAL_PUBLIC_KEY_E, 0, 1, VOTE_MIX_MOCK, VOTE_MIX_MOCK);
		verify(verifyMixElementRevelation.VerifyMixVoteElementRevelation).apply(
				VOTE_ELEMENT_REVELATION_MOCK, EL_GAMAL_PUBLIC_KEY_E, 0, 1,
				VOTE_MIX_MOCK, VOTE_MIX_MOCK);
	}

	@Test
	@DisplayName("If the revelation is neither about vote nor capability, an IllegalArgumentException is thrown")
	void test2() {
		assertThrows(IllegalArgumentException.class,
				() -> verifyMixElementRevelation.apply(MIX_ELEMENT_REVELATION_MOCK,
						EL_GAMAL_PUBLIC_KEY_E, 0, 1, CAPABILITY_MIX_MOCK,
						CAPABILITY_MIX_MOCK));
	}

	@Test
	@DisplayName("if the revelation is null, a NullPointerException is thrown")
	void test3() {
		assertThrows(NullPointerException.class,
				() -> verifyMixElementRevelation.apply(null, EL_GAMAL_PUBLIC_KEY_E, 0,
						1, CAPABILITY_MIX_MOCK, CAPABILITY_MIX_MOCK));
	}

	@Test
	@DisplayName("if key is null, a NullPointerException is thrown")
	void test4() {
		assertThrows(NullPointerException.class,
				() -> verifyMixElementRevelation.apply(MIX_ELEMENT_REVELATION_MOCK,
						null, 0, 1, CAPABILITY_MIX_MOCK, CAPABILITY_MIX_MOCK));
	}

	@Test
	@DisplayName("if fromMix is null, a NullPointerException is thrown")
	void test5() {
		assertThrows(NullPointerException.class,
				() -> verifyMixElementRevelation.apply(MIX_ELEMENT_REVELATION_MOCK,
						EL_GAMAL_PUBLIC_KEY_E, 0, 1, null, CAPABILITY_MIX_MOCK));
	}

	@Test
	@DisplayName("if toMix is null, a NullPointerException is thrown")
	void test6() {
		assertThrows(NullPointerException.class,
				() -> verifyMixElementRevelation.apply(MIX_ELEMENT_REVELATION_MOCK,
						EL_GAMAL_PUBLIC_KEY_E, 0, 1, CAPABILITY_MIX_MOCK, null));
	}

}
