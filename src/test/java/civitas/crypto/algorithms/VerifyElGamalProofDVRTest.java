package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.util.Tested;

public class VerifyElGamalProofDVRTest extends ConcreteTestBase
		implements ElGamalProofDVRCTestData {

	@Tested
	VerifyElGamalProofDVR verifyElGamalProofDVR;

	@Test
	@DisplayName("verify works")
	void test2() {
		assertTrue(verifyElGamalProofDVR.apply(EL_GAMAL_PROOF_DVR,
				EL_GAMAL_PUBLIC_KEY, EL_GAMAL_PUBLIC_KEY2));
	}

}
