package civitas.crypto.proofdvr.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.privatekey.tests.ElGamalPrivateKeyTestData;
import civitas.crypto.proofdvr.FakeElGamalProofDVR;

class FakeElGamalProofDVRTest extends RandomAwareTestBase
		implements ElGamalProofDVRTestData, ElGamalPrivateKeyTestData {

	@InjectMocks
	FakeElGamalProofDVR fakeElGamalProofDVRC;

	@Test
	@DisplayName("creates a fake proof with the private key of the verifier which verifies")
	void test1_1() {

		assertEquals(
				FAKE_PROOF_DVR,
				fakeElGamalProofDVRC.apply(
						CIPHERTEXT_E,
						CIPHERTEXT_EPRIME,
						EL_GAMAL_PUBLIC_KEY_E,
						EL_GAMAL_PUBLIC_KEY_EPRIME,
						EL_GAMAL_PRIVATE_KEY_EPRIME));
	}

	@Test
	@DisplayName("the version with arguments in different order also works" + " FIXME: why do we have two versions?")
	void test1_2() {

		assertEquals(
				FAKE_PROOF_DVR,
				fakeElGamalProofDVRC.apply(
						EL_GAMAL_PUBLIC_KEY_E,
						EL_GAMAL_PUBLIC_KEY_EPRIME,
						EL_GAMAL_PRIVATE_KEY_EPRIME,
						CIPHERTEXT_E,
						CIPHERTEXT_EPRIME));
	}
}
