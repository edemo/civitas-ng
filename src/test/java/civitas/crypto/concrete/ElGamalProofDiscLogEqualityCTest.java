package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.algorithms.ConstructElGamalDiscLogEqualityProof;
import civitas.util.CivitasBigInteger;
import civitas.util.DI;
import civitas.util.Use;

public class ElGamalProofDiscLogEqualityCTest
		implements ElGamalProofDiscLogEqualityCTestData {

	@Use
	private static ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof = DI
			.get(ConstructElGamalDiscLogEqualityProof.class);

	CryptoFactoryC factory = CryptoFactoryC.singleton();

	@Test
	@DisplayName("constructor and toXML works as expected ")
	void test() throws IllegalArgumentException, IOException {

		assertEquals(EL_GAMAL_PROOF_DISC_LOG_EQUALITY_NAIVE_XML,
				new ElGamalProofDiscLogEqualityC(BIGINT_G, GENERATOR_OTHER, BIGINT_A,
						BIGINT_B, BIGINT_C, BIGINT_D, BIGINT_A_ENCRYPTED_SAFE,
						BIGINT_G_OTHER).toXML());

	}

	@Test
	@DisplayName("construcProof constructs a proof "
			+ "To prove that log v = log w, where v = g_1^x and w = g_2^x,"
			+ "let: z = random in Z_q, a = g_1^z b = g_2^z c = hash(v,w,a,b) "
			+ "r = (z + cx) mod q		  The proof is (a,b,c,r). ")
	void construcProofTest() {
		CivitasBigInteger g1 = PUBKEY_VALUE_OTHER_GENERATOR_RANDOM1_FACTOR;
		CivitasBigInteger g2 = BIGINT_G;
		CivitasBigInteger x = BIGINT_A;
		CivitasBigInteger v = g1.modPow(x, BIGINT_P);
		CivitasBigInteger w = g2.modPow(x, BIGINT_P);
		CivitasBigInteger z = RANDOMS_0;
		CivitasBigInteger a = g1.modPow(z, BIGINT_P);
		CivitasBigInteger b = g2.modPow(z, BIGINT_P);
		CivitasBigInteger c = factory
				.hashToBigInt(factory.hash(Arrays.asList(v, w, a, b))).mod(BIGINT_Q);
		CivitasBigInteger r = z.modAdd(c.modMultiply(x, BIGINT_Q), BIGINT_Q);

		TestUtil.setUpFakeRandom();

		ElGamalProofDiscLogEqualityC proof = constructElGamalDiscLogEqualityProof
				.apply(EL_GAMAL_PARAMETERS_GENERATOR_OTHER, g1, g2, x);
		assertEquals(g1, proof.g1);
		assertEquals(g2, proof.g2);
		assertEquals(v, proof.v);
		assertEquals(a, proof.a);
		assertEquals(w, proof.w);
		assertEquals(b, proof.b);
		assertEquals(c, proof.c);
		assertEquals(r, proof.r);
		assertEquals(EL_GAMAL_PROOF_DISC_LOG_EQUALITY_XML, proof.toXML());

		TestUtil.tearDownFakeRandom();

	}

	@Test
	@DisplayName("verify is true for a correctly costructed proof "
			+ "To verify, check that g_1^r = av^c (mod p) and g_2^r = bw^c (mod p).")
	void test0_1() {
		assertTrue(EL_GAMAL_PROOF_DISC_LOG_EQUALITY.verify(EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify is false for a bad proof ")
	void test0_2() {
		assertFalse(new ElGamalProofDiscLogEqualityC(BIGINT_G, GENERATOR_OTHER,
				BIGINT_A, BIGINT_B, BIGINT_C, BIGINT_D, BIGINT_A_ENCRYPTED_SAFE,
				BIGINT_G_OTHER).verify(EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify is false for a bad proof (other branch)")
	void test0_3() throws IllegalArgumentException, IOException {
		assertFalse(((ElGamalProofDiscLogEqualityC) ElGamalProofDiscLogEqualityC
				.fromXML(new StringReader(EL_GAMAL_PROOF_DISC_LOG_EQUALITY_BAD_W_XML)))
				.verify(EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify is false for an empty proof")
	void test0_4() throws IllegalArgumentException, IOException {
		assertFalse(new ElGamalProofDiscLogEqualityC(null, null, null, null, null,
				null, null, null).verify(EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("verify is false if the parameters are not of ElGamalParametersC")
	void test0_5() {
		assertFalse(
				EL_GAMAL_PROOF_DISC_LOG_EQUALITY.verify(mock(ElGamalParameters.class)));
	}

	@Test
	@DisplayName("can be constructed with all nulls "
			+ "FIXME: is there a reason for it?")
	void test1() {
		assertEquals(EL_GAMAL_PROOF_DISC_LOG_EQUALITY_NULL_XML,
				new ElGamalProofDiscLogEqualityC(null, null, null, null, null, null,
						null, null).toXML());
	}

	@Test
	@DisplayName("fromXML works as expected ")
	void test2() {
		assertEquals(EL_GAMAL_PROOF_DISC_LOG_EQUALITY_XML,
				EL_GAMAL_PROOF_DISC_LOG_EQUALITY.toXML());
	}

}
