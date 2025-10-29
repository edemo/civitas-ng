package civitas.crypto.keypairshare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.keypairshare.ElGamalKeyPairShare;
import civitas.crypto.keypairshare.GenerateKeyPairShare;
import civitas.crypto.parameters.tests.ElGamalParametersTestData;

class GenerateKeyPairShareTest extends RandomAwareTestBase implements ElGamalParametersTestData {

	@InjectMocks
	GenerateKeyPairShare generateKeyPairShare;

	@Test
	@DisplayName("generateKeyPairShare works as expected"
			+ "x := random, h:=g^x (mod p). x is the private, h is the public key")
	void generateKeyPairShareTest() {

		ElGamalKeyPairShare keyPair = generateKeyPairShare.apply(EL_GAMAL_PARAMETERS);

		assertEquals(RANDOMS_0, keyPair.privKey().x());
		assertEquals(RANDOMS_0_PUBLISHED, keyPair.pubKey().y);
	}
}
