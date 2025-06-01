package civitas.crypto.proofdvr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.privatekey.ElGamalPrivateKeyTestData;
import civitas.util.Tested;
import civitas.util.Use;

public class FakeElGamalProofDVRCTest extends TestBase
		implements ElGamalProofDVRCTestData, ElGamalPrivateKeyTestData {

	@Tested
	FakeElGamalProofDVR fakeElGamalProofDVRC;

	@Use
	VerifyElGamalProofDVR verifyElGamalProofDVR;

	@Test
	@DisplayName("creates a fake proof with the private key of the verifier which verifies")
	void test1_1() throws IllegalArgumentException, IOException {

		ElGamalProofDVR proof = fakeElGamalProofDVRC.apply(CIPHERTEXT_E,
				CIPHERTEXT_EPRIME, EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_EPRIME,
				EL_GAMAL_PRIVATE_KEY_EPRIME);
		assertTrue(verifyElGamalProofDVR.apply(proof, EL_GAMAL_PUBLIC_KEY_E,
				EL_GAMAL_PUBLIC_KEY_EPRIME));
	}

	@Test
	@DisplayName("the version with arguments in different order also works"
			+ " FIXME: why do we have two versions?")
	void test1_2() throws IllegalArgumentException, IOException {

		ElGamalProofDVR proof = fakeElGamalProofDVRC.apply(EL_GAMAL_PUBLIC_KEY_E,
				EL_GAMAL_PUBLIC_KEY_EPRIME, EL_GAMAL_PRIVATE_KEY_EPRIME, CIPHERTEXT_E,
				CIPHERTEXT_EPRIME);
		assertTrue(verifyElGamalProofDVR.apply(proof, EL_GAMAL_PUBLIC_KEY_E,
				EL_GAMAL_PUBLIC_KEY_EPRIME));
	}

}
