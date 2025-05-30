package civitas.crypto.keypairshare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.parameters.ElGamalParametersCTestData;
import civitas.crypto.privatekey.ElGamalPrivateKeyC;
import civitas.crypto.publickey.ElGamalPublicKeyC;
import civitas.util.Tested;

public class GenerateKeyPairShareTest extends ConcreteTestBase
		implements ElGamalParametersCTestData {

	@Tested
	GenerateKeyPairShare generateKeyPairShare;

	@Test
	@DisplayName("generateKeyPairShare works as expected"
			+ "x := random, h:=g^x (mod p). x is the private, h is the public key")
	void generateKeyPairShareTest() {

		ElGamalKeyPairShare keyPair = generateKeyPairShare
				.apply(EL_GAMAL_PARAMETERS);

		assertEquals(RANDOMS_0, ((ElGamalPrivateKeyC) keyPair.privKey).x);
		assertEquals(RANDOMS_0_PUBLISHED, ((ElGamalPublicKeyC) keyPair.pubKey).y);

	}

}
