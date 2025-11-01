package civitas.crypto.keypair.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.CommonUtil;
import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.keypair.ElGamalKeyPair;
import civitas.crypto.keypair.GenerateElGamalKeyPair;
import civitas.crypto.parameters.tests.ElGamalParametersTestData;

class GenerateElGamalKeyPairTest extends RandomAwareTestBase implements ElGamalParametersTestData {

	@InjectMocks
	GenerateElGamalKeyPair generateElGamalKeyPair;

	@Test
	@DisplayName("generateElGamalKeyPair works as expected"
			+ "x := random, h:=g^x (mod p). x is the private, h is the public key")
	void generateElGamalKeyPairtest() {
		ElGamalKeyPair keyPair = generateElGamalKeyPair.apply(EL_GAMAL_PARAMETERS);
		assertEquals(
				RANDOMS_0_BASE64, CommonUtil.fromBigInt(keyPair.privateKey().x()));
		assertEquals(RANDOMS_0_PUBLISHED, keyPair.publicKey().y);
	}
}
