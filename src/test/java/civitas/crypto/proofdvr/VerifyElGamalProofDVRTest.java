package civitas.crypto.proofdvr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

public class VerifyElGamalProofDVRTest extends TestBase
		implements ElGamalProofDVRCTestData {

	@Tested
	VerifyElGamalProofDVR verifyElGamalProofDVR;

	@Test
	@DisplayName("verify works")
	void test2() {
		assertTrue(verifyElGamalProofDVR.apply(EL_GAMAL_PROOF_DVR,
				EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_EPRIME));

	}

}
