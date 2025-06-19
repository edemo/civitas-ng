package civitas.crypto.proofdvr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.common.TestBase;
import civitas.crypto.privatekey.ElGamalPrivateKeyTestData;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
public class FakeElGamalProofDVRTest extends TestBase
		implements ElGamalProofDVRTestData, ElGamalPrivateKeyTestData {

	@InjectMocks
	FakeElGamalProofDVR fakeElGamalProofDVRC;

	@Test
	@DisplayName("creates a fake proof with the private key of the verifier which verifies")
	void test1_1() throws IllegalArgumentException, IOException {

		assertEquals(FAKE_PROOF_DVR,
				fakeElGamalProofDVRC.apply(CIPHERTEXT_E, CIPHERTEXT_EPRIME,
						EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_EPRIME,
						EL_GAMAL_PRIVATE_KEY_EPRIME));
	}

	@Test
	@DisplayName("the version with arguments in different order also works"
			+ " FIXME: why do we have two versions?")
	void test1_2() throws IllegalArgumentException, IOException {

		assertEquals(FAKE_PROOF_DVR,
				fakeElGamalProofDVRC.apply(EL_GAMAL_PUBLIC_KEY_E,
						EL_GAMAL_PUBLIC_KEY_EPRIME, EL_GAMAL_PRIVATE_KEY_EPRIME,
						CIPHERTEXT_E, CIPHERTEXT_EPRIME));
	}

}
