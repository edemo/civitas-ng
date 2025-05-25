package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoException;
import civitas.crypto.MessageDigest;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalCiphertextCTestData;
import civitas.crypto.concrete.ElGamalReencryptFactorCTestData;
import civitas.crypto.concrete.ProofVoteC;
import civitas.crypto.concrete.ProofVoteTestData;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class ConstructProofVoteTest extends ConcreteTestBase
		implements ElGamalCiphertextCTestData, ElGamalReencryptFactorCTestData,
		ProofVoteTestData {

	@Tested
	ConstructProofVote constructProofVote;

	@Use
	CryptoHash cryptoHash;
	@Use
	ObtainMessageDigest obtainMessageDigest;

	@Test
	@DisplayName("constructor with vote constructs the proof"
			+ "		 r1 = random in Z_q,                          "
			+ "		 r2 = random in Z_q,                           "
			+ "		 a = g^r1,                                      "
			+ "		 b = g^r2,                           "
			+ "		 c = hash(g, encCapability (a and b), encChoice (a and b), context,a,b,g^r1,g^r2) mod q, "
			+ "		 s1 = r1-c*alpha1 (mod q),                           "
			+ "		 s2 = r2-c*alpha2 (mod q) ")
	void test2() throws CryptoException {
		CivitasBigInteger r1 = RANDOMS_0;
		CivitasBigInteger r2 = RANDOMS_1;
		CivitasBigInteger a = BIGINT_G.modPow(r1, BIGINT_P);
		CivitasBigInteger b = BIGINT_G.modPow(r2, BIGINT_P);
		MessageDigest md = obtainMessageDigest.apply();
		md.update(SOMESTRING_EXTENDED.getBytes());
		CivitasBigInteger somestringDigest = new CivitasBigInteger(1, md.digest());
		List<CivitasBigInteger> proofEnv = Arrays.asList(BIGINT_G,
				EL_GAMAL_CIPHERTEXT_A.a, EL_GAMAL_CIPHERTEXT_A.b,
				EL_GAMAL_CIPHERTEXT_B.a, EL_GAMAL_CIPHERTEXT_B.b, somestringDigest, a,
				b);

		CivitasBigInteger c = new CivitasBigInteger(1, cryptoHash.apply(proofEnv))
				.mod(BIGINT_Q);
		CivitasBigInteger s1 = r1.modSubtract(
				c.modMultiply(ELGAMAL_REENCRYPT_FACTOR_C.r, EL_GAMAL_PARAMETERS.q),
				EL_GAMAL_PARAMETERS.q);
		CivitasBigInteger s2 = r2.modSubtract(
				c.modMultiply(ELGAMAL_REENCRYPT_FACTOR_D.r, EL_GAMAL_PARAMETERS.q),
				EL_GAMAL_PARAMETERS.q);

		ProofVoteC proof = constructProofVote.apply(EL_GAMAL_PARAMETERS,
				EL_GAMAL_CIPHERTEXT_A, EL_GAMAL_CIPHERTEXT_B, SOMESTRING_EXTENDED,
				ELGAMAL_REENCRYPT_FACTOR_C, ELGAMAL_REENCRYPT_FACTOR_D);
		assertEquals(proof.c, c);
		assertEquals(proof.s1, s1);
		assertEquals(proof.s2, s2);
		assertEquals(PROOF_VOTE_XML, proof.toXML());
	}

}
