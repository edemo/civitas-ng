package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoException;

public class ElGamalProof1OfLCTest implements ElGamal1OfLReencryptionCTestData,
		ElGamalReencryptFactorCTestData {

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() throws CryptoException {

		assertEquals(EL_GAMAL_PROOF_1_OF_L_XML, EL_GAMAL_PROOF_1_OF_L.toXML());
	}

}
