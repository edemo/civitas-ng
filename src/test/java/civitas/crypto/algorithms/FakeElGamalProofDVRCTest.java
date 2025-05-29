package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalPrivateKeyCTestData;
import civitas.crypto.concrete.ElGamalProofDVRC;
import civitas.util.Tested;
import civitas.util.Use;

public class FakeElGamalProofDVRCTest extends ConcreteTestBase
		implements ElGamalProofDVRCTestData, ElGamalPrivateKeyCTestData {

	@Tested
	FakeElGamalProofDVRC fakeElGamalProofDVRC;

	@Use
	VerifyElGamalProofDVR verifyElGamalProofDVR;

	@Test
	@DisplayName("creates a fake proof with the private key of the verifier which verifies")
	void test1_1() throws IllegalArgumentException, IOException {

		ElGamalProofDVRC proof = fakeElGamalProofDVRC.apply(CIPHERTEXT_E,
				CIPHERTEXT_EPRIME, EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_EPRIME,
				ELGAMAL_PRIVATE_KEY_EPRIME);
		assertTrue(verifyElGamalProofDVR.apply(proof, EL_GAMAL_PUBLIC_KEY_E,
				EL_GAMAL_PUBLIC_KEY_EPRIME));
	}

}
