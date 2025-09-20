package civitas.crypto.proofdvr;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import io.github.magwas.testing.TestBase;

class VerifyElGamalProofDVRTest extends TestBase implements ElGamalProofDVRTestData {

	@InjectMocks
	VerifyElGamalProofDVR verifyElGamalProofDVR;

	@Test
	@DisplayName("verify works")
	void test2() {
		assertTrue(verifyElGamalProofDVR.apply(EL_GAMAL_PROOF_DVR, EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_EPRIME));
	}

	@Test
	@DisplayName("if the proof fails, false is returned")
	void test2_1() {
		assertFalse(
				verifyElGamalProofDVR.apply(EL_GAMAL_PROOF_DVR_BAD, EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_EPRIME));
	}

	@Test
	@DisplayName("verify works for a fake proof")
	void test() {
		assertTrue(verifyElGamalProofDVR.apply(FAKE_PROOF_DVR, EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_EPRIME));
	}
}
