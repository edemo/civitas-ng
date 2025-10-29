package civitas.crypto.proofknowndisclog.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.proofknowndisclog.ConstructProofKnowDiscLog;

class ConstructProofKnowDiscLogTest extends RandomAwareTestBase implements ProofKnowDiscLogTestData {

	@InjectMocks
	ConstructProofKnowDiscLog constructProofKnowDiscLog;

	@Autowired
	CryptoHash cryptoHash;

	@Test
	@DisplayName("constructProofKnowDiscLog constructs the proof correctly:"
			+ "v:=g^key (mod p), " + "z:=random, " + "a:=g^z (mod p), "
			+ "c:=hash(v,a)%q, " + "r:=z+c*key (mod q)")
	void constructProofKnowDiscLogTest() {

		assertEquals(
				EL_GAMAL_PROOF_KNOWN_DISC_LOG,
				constructProofKnowDiscLog.apply(EL_GAMAL_PARAMETERS, EL_GAMAL_PRIVATE_KEY_E));
	}

	@Test
	@DisplayName("constructProofKnowDiscLog returns null if the key is null")
	void constructProofKnowDiscLogTest1() {

		assertNull(constructProofKnowDiscLog.apply(EL_GAMAL_PARAMETERS, null));
	}

	@Test
	@DisplayName("constructProofKnowDiscLog returns null if the parameters is null")
	void constructProofKnowDiscLogTest3() {

		assertNull(constructProofKnowDiscLog.apply(null, EL_GAMAL_PRIVATE_KEY_E));
	}
}
