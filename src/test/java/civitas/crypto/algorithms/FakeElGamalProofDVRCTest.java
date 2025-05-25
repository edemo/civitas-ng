package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalProofDVRC;
import civitas.util.Tested;
import civitas.util.Use;

public class FakeElGamalProofDVRCTest extends ConcreteTestBase
		implements ElGamalProofDVRCTestData {

	@Tested
	FakeElGamalProofDVRC fakeElGamalProofDVRC;

	@Use
	VerifyElGamalProofDVR verifyElGamalProofDVR;

	@Test
	@DisplayName("fakeProof can create a fake proof which verifies with the private key of the verifier")
	void test1_1() throws IllegalArgumentException, IOException {
		ElGamalProofDVRC proof = fakeElGamalProofDVRC.apply(EL_GAMAL_CIPHERTEXT_E,
				EL_GAMAL_CIPHERTEXT_A_ENCRYPTED_WITH_FACTOR_A, EL_GAMAL_PUBLIC_KEY, EL_GAMAL_PUBLIC_KEY2,
				ELGAMAL_PRIVATE_KEY2);
		assertTrue(verifyElGamalProofDVR.apply(proof, EL_GAMAL_PUBLIC_KEY,
				EL_GAMAL_PUBLIC_KEY2));
	}

}
