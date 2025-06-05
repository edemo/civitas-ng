package civitas.crypto.proof1ofl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.CryptoException;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionTestData;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorCTestData;
import civitas.util.Use;

public class ElGamalProof1OfLCTest extends TestBase implements
		ElGamal1OfLReencryptionTestData, ElGamalReencryptFactorCTestData {

	@Use
	ElGamalProof1OfLToXML elGamalProof1OfLToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() throws CryptoException {

		assertEquals(EL_GAMAL_PROOF_1_OF_L_XML,
				elGamalProof1OfLToXML.apply(EL_GAMAL_PROOF_1_OF_L));
	}

}
