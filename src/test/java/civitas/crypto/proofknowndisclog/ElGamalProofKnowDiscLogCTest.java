package civitas.crypto.proofknowndisclog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.common.TestBase;
import civitas.common.Util;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.util.CivitasBigInteger;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
public class ElGamalProofKnowDiscLogCTest extends TestBase
		implements ProofKnowDiscLogTestData {

	@Autowired
	CryptoHash cryptoHash;

	@Test
	@Tag("testdata")
	@DisplayName("EL_GAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64")
	void testHash() {
		CivitasBigInteger hash = cryptoHash
				.apply(EL_GAMAL_PROOF_KNOWN_DISC_LOG_V, EL_GAMAL_PROOF_KNOWN_DISC_LOG_A)
				.mod(BIGINT_Q);
		assertEquals(EL_GAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64, Util.fromBigInt(hash));

	}

}
