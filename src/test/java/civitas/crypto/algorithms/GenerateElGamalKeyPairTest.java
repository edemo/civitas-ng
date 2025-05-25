package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.crypto.ElGamalKeyPair;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalParametersCTestData;
import civitas.crypto.concrete.ElGamalPrivateKeyC;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.util.Tested;

public class GenerateElGamalKeyPairTest extends ConcreteTestBase
		implements ElGamalParametersCTestData {

	@Tested
	GenerateElGamalKeyPair generateElGamalKeyPair;

	@Test
	@DisplayName("generateElGamalKeyPair works as expected"
			+ "x := random, h:=g^x (mod p). x is the private, h is the public key")
	void generateElGamalKeyPairtest() {
		ElGamalKeyPair keyPair = generateElGamalKeyPair.apply(EL_GAMAL_PARAMETERS);
		assertEquals(RANDOMS_0_BASE64,
				Util.fromBigInt(((ElGamalPrivateKeyC) keyPair.privateKey()).x));
		assertEquals(RANDOMS_0_PUBLISHED,
				((ElGamalPublicKeyC) keyPair.publicKey()).y);

	}

}
