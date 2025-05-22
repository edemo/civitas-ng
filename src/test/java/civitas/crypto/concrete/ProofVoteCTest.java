package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.crypto.CryptoException;
import civitas.crypto.ProofVote;

public class ProofVoteCTest extends ConcreteTestBase
		implements ConcreteTestData {

	private static final ProofVoteC PROOF_VOTE = new ProofVoteC(PROOF_VOTE_C,
			PROOF_VOTE_S1, PROOF_VOTE_S2);

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PROOF_VOTE_XML, PROOF_VOTE.toXML());
	}

	BigInteger hash(BigInteger a, BigInteger b, BigInteger c)
			throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(a.toByteArray());
		digest.update(b.toByteArray());
		digest.update(c.toByteArray());
		return new BigInteger(digest.digest());
	}

	BigInteger fromBase64(String base64) {
		return new BigInteger(Base64.getDecoder().decode(base64));
	}

	@Test
	@DisplayName("constructor with vote constructs the proof"
			+ "		 r1 = random in Z_q,                          "
			+ "		 r2 = random in Z_q,                           "
			+ "		 a = g^r1,                                      "
			+ "		 b = g^r2,                           "
			+ "		 c = hash(params, encCapability, encChoice, context,a,b) mod q, "
			+ "		 s1 = r1-c*alpha1 (mod q),                           "
			+ "		 s2 = r2-c*alpha2 (mod q) ")
	void test2() throws CryptoException {

		TestUtil.setUpFakeRandom();
		ProofVoteC proof = new ProofVoteC(EL_GAMAL_PARAMETERS,
				EL_GAMAL_CIPHERTEXT_A, EL_GAMAL_CIPHERTEXT_B, SOMESTRING_EXTENDED,
				ENCRYPT_FACTOR_C, ENCRYPT_FACTOR_D);
		assertEquals(PROOF_VOTE_XML, proof.toXML());
		TestUtil.tearDownFakeRandom();
	}

	@Test
	@DisplayName("verify checks if c equals "
			+ "	 hash(params, encCapability, encChoice, context,"
			+ "	   g^s1*encCapability.a^c (mod p),"
			+ "	   g^s2*encChoice.a^c (mod p))")
	void test2_1() {

		assertTrue(PROOF_VOTE.verify(EL_GAMAL_PARAMETERS, EL_GAMAL_CIPHERTEXT_A,
				EL_GAMAL_CIPHERTEXT_B, SOMESTRING_EXTENDED));
	}

	@Test
	@DisplayName("equals and fromXML works as expected")
	void test3() throws IllegalArgumentException, IOException {

		assertTrue(PROOF_VOTE
				.equals(ProofVoteC.fromXML(new StringReader(PROOF_VOTE_XML))));
	}

	@Test
	@DisplayName("null ProofVote does not equal itself")
	void test4() {
		assertFalse(new ProofVoteC(null, null, null)
				.equals(new ProofVoteC(null, null, null)));
	}

	@Test
	@DisplayName("does not equal anything not of type ProofVoteC")
	void test5() {

		assertFalse(PROOF_VOTE.equals(mock(ProofVote.class)));
	}

	@Test
	@DisplayName("not equals if c does not equals")
	void test6() {

		assertFalse(PROOF_VOTE
				.equals(new ProofVoteC(BIGINT_P, PROOF_VOTE_S1, PROOF_VOTE_S2)));
	}

	@Test
	@DisplayName("not equals if s1 does not equals")
	void test6_1() {

		assertFalse(PROOF_VOTE
				.equals(new ProofVoteC(PROOF_VOTE_C, BIGINT_P, PROOF_VOTE_S2)));
	}

	@Test
	@DisplayName("not equals if s2 does not equals")
	void test6_2() {

		assertFalse(PROOF_VOTE
				.equals(new ProofVoteC(PROOF_VOTE_C, PROOF_VOTE_S1, BIGINT_P)));
	}

	@Test
	@DisplayName("constructor accepts nulls")
	void test1() {
		assertEquals(PROOF_VOTE_NULL_XML, new ProofVoteC(null, null, null).toXML());
	}

}
