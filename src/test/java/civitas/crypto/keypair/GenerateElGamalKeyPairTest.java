package civitas.crypto.keypair;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.common.Util;
import civitas.crypto.parameters.ElGamalParametersCTestData;
import civitas.util.Tested;

public class GenerateElGamalKeyPairTest extends TestBase
		implements ElGamalParametersCTestData {

	@Tested
	GenerateElGamalKeyPair generateElGamalKeyPair;

	@Test
	@DisplayName("generateElGamalKeyPair works as expected"
			+ "x := random, h:=g^x (mod p). x is the private, h is the public key")
	void generateElGamalKeyPairtest() {
		ElGamalKeyPair keyPair = generateElGamalKeyPair.apply(EL_GAMAL_PARAMETERS);
		assertEquals(RANDOMS_0_BASE64, Util.fromBigInt(keyPair.privateKey.x));
		assertEquals(RANDOMS_0_PUBLISHED, keyPair.publicKey.y);

	}

}
