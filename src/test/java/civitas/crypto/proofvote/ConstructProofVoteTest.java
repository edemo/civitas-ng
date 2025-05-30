package civitas.crypto.proofvote;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.crypto.ConcreteTestBase;
import civitas.crypto.CryptoException;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.external.ObtainMessageDigest;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class ConstructProofVoteTest extends ConcreteTestBase
		implements ProofVoteTestData {

	@Tested
	ConstructProofVote constructProofVote;
	@Use
	CalculateProofEnvironment calculateProofEnvironment;
	@Use
	CryptoHash cryptoHash;
	@Use
	ObtainMessageDigest obtainMessageDigest;

	@Test
	@DisplayName("constructs a proof that the vote data is correct"
			+ "		 r1 = random in Z_q,                          "
			+ "		 r2 = random in Z_q,                           "
			+ "		 a = g^r1,                                      "
			+ "		 b = g^r2,                           "
			+ "		 c = hash(g, encCapability (a and b), encChoice (a and b), context,a,b,g^r1,g^r2) mod q, "
			+ "		 s1 = r1-c*alpha1 (mod q),                           "
			+ "		 s2 = r2-c*alpha2 (mod q) ")
	void test2() throws CryptoException {

		ProofVoteC proofVote = constructProofVote.apply(EL_GAMAL_PARAMETERS,
				CIPHERTEXT_ENCCAP, REENCRYPTED_WELL_KNOWN_CHOICE, ADDITIONALENV,
				ELGAMAL_REENCRYPT_FACTOR_EPRIME, ELGAMAL_REENCRYPT_FACTOR_E);
		assertEquals(PROOF_VOTE_XML, proofVote.toXML());
	}

	@Test
	@Tag("testdata")
	@DisplayName("PROOF_VOTE_C_BASE64")
	void testData() {
		CivitasBigInteger r1 = RANDOMS_0;
		CivitasBigInteger r2 = RANDOMS_1;
		List<CivitasBigInteger> E = calculateProofEnvironment.apply(
				EL_GAMAL_PARAMETERS, CIPHERTEXT_ENCCAP, REENCRYPTED_WELL_KNOWN_CHOICE,
				ADDITIONALENV);
		E.add(BIGINT_G.modPow(r1, BIGINT_P));
		E.add(BIGINT_G.modPow(r2, BIGINT_P));

		CivitasBigInteger PROOF_VOTE_C = new CivitasBigInteger(1,
				cryptoHash.apply(E)).mod(BIGINT_Q);
		assertEquals(PROOF_VOTE_C_BASE64, Util.fromBigInt(PROOF_VOTE_C));

	}

}
