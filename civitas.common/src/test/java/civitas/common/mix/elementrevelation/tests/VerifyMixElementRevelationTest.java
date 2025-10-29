package civitas.common.mix.elementrevelation.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.mix.capabilityelementrevelation.VerifyMixCapabilityElementRevelation;
import civitas.common.mix.capabilitymix.tests.CapabilityMixTestData;
import civitas.common.mix.capabilitymixrevelation.tests.MixCapabilityElementRevelationTestData;
import civitas.common.mix.elementrevelation.VerifyMixElementRevelation;
import civitas.common.mix.voteelementrevelation.VerifyMixVoteElementRevelation;
import civitas.common.mix.voteelementrevelation.tests.MixVoteElementRevelationTestData;
import civitas.common.mix.votemix.tests.VoteMixTestData;
import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.publickey.tests.ElGamalPublicKeyTestData;
import io.github.magwas.konveyor.testing.TestUtil;

class VerifyMixElementRevelationTest extends RandomAwareTestBase
		implements ElGamalPublicKeyTestData,
				MixCapabilityElementRevelationTestData,
				CapabilityMixTestData,
				MixVoteElementRevelationTestData,
				VoteMixTestData,
				MixElementRevelationTestData {

	@InjectMocks
	VerifyMixElementRevelation verifyMixElementRevelation;

	private VerifyMixCapabilityElementRevelation verifyMixCapabilityElementRevelation;
	private VerifyMixVoteElementRevelation verifyMixVoteElementRevelation;

	@BeforeEach
	@Override
	public void setUp() throws Throwable {
		super.setUp();
		verifyMixCapabilityElementRevelation =
				TestUtil.dependency(verifyMixElementRevelation, VerifyMixCapabilityElementRevelation.class);
		verifyMixVoteElementRevelation =
				TestUtil.dependency(verifyMixElementRevelation, VerifyMixVoteElementRevelation.class);
	}

	@Test
	@DisplayName("If the revelation is about capability, uses verifyMixCapabilityElementRevelation")
	void test() {
		verifyMixElementRevelation.apply(
				CAPABILITY_ELEMENT_RELEVATION_MOCK,
				EL_GAMAL_PUBLIC_KEY_E,
				0,
				1,
				CAPABILITY_MIX_MOCK,
				CAPABILITY_MIX_MOCK);
		verify(verifyMixCapabilityElementRevelation)
				.apply(
						CAPABILITY_ELEMENT_RELEVATION_MOCK,
						EL_GAMAL_PUBLIC_KEY_E,
						0,
						1,
						CAPABILITY_MIX_MOCK,
						CAPABILITY_MIX_MOCK);
	}

	@Test
	@DisplayName("If the revelation is about vote, uses verifyMixVoteElementRevelation")
	void test1() {
		verifyMixElementRevelation.apply(
				VOTE_ELEMENT_REVELATION_MOCK, EL_GAMAL_PUBLIC_KEY_E, 0, 1, VOTE_MIX_MOCK, VOTE_MIX_MOCK);
		verify(verifyMixVoteElementRevelation)
				.apply(VOTE_ELEMENT_REVELATION_MOCK, EL_GAMAL_PUBLIC_KEY_E, 0, 1, VOTE_MIX_MOCK, VOTE_MIX_MOCK);
	}

	@Test
	@DisplayName("If the revelation is neither about vote nor capability, an IllegalArgumentException is thrown")
	void test2() {
		assertThrows(
				IllegalArgumentException.class,
				() -> verifyMixElementRevelation.apply(
						MIX_ELEMENT_REVELATION_MOCK,
						EL_GAMAL_PUBLIC_KEY_E,
						0,
						1,
						CAPABILITY_MIX_MOCK,
						CAPABILITY_MIX_MOCK));
	}

	@Test
	@DisplayName("if the revelation is null, a NullPointerException is thrown")
	void test3() {
		assertThrows(
				NullPointerException.class,
				() -> verifyMixElementRevelation.apply(
						null, EL_GAMAL_PUBLIC_KEY_E, 0, 1, CAPABILITY_MIX_MOCK, CAPABILITY_MIX_MOCK));
	}

	@Test
	@DisplayName("if key is null, a NullPointerException is thrown")
	void test4() {
		assertThrows(
				NullPointerException.class,
				() -> verifyMixElementRevelation.apply(
						MIX_ELEMENT_REVELATION_MOCK, null, 0, 1, CAPABILITY_MIX_MOCK, CAPABILITY_MIX_MOCK));
	}

	@Test
	@DisplayName("if fromMix is null, a NullPointerException is thrown")
	void test5() {
		assertThrows(
				NullPointerException.class,
				() -> verifyMixElementRevelation.apply(
						MIX_ELEMENT_REVELATION_MOCK, EL_GAMAL_PUBLIC_KEY_E, 0, 1, null, CAPABILITY_MIX_MOCK));
	}

	@Test
	@DisplayName("if toMix is null, a NullPointerException is thrown")
	void test6() {
		assertThrows(
				NullPointerException.class,
				() -> verifyMixElementRevelation.apply(
						MIX_ELEMENT_REVELATION_MOCK, EL_GAMAL_PUBLIC_KEY_E, 0, 1, CAPABILITY_MIX_MOCK, null));
	}
}
