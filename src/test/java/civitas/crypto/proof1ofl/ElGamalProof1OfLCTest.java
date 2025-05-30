package civitas.crypto.proof1ofl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoException;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionCTestData;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorCTestData;

public class ElGamalProof1OfLCTest implements ElGamal1OfLReencryptionCTestData,
		ElGamalReencryptFactorCTestData {

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() throws CryptoException {

		assertEquals(EL_GAMAL_PROOF_1_OF_L_XML, EL_GAMAL_PROOF_1_OF_L.toXML());
	}

}
