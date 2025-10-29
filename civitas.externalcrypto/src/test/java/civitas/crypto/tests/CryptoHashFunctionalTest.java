package civitas.crypto.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.ballotdesign.tests.BallotDesignTestData;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.messagedigest.tests.MessageDigestTestData;
import civitas.crypto.signedciphertext.tests.ElGamalSignedCiphertextTestData;
import civitas.util.CivitasBigIntegerFactory;
import io.github.magwas.konveyor.testing.TestBase;

@Tag("functional")
class CryptoHashFunctionalTest extends TestBase
		implements MessageDigestTestData, BallotDesignTestData, ElGamalSignedCiphertextTestData {

	@InjectMocks
	CryptoHash cryptoHash;

	@Test
	@DisplayName("if updated with (byte[]) null, nothing happens")
	void test2() {
		assertArrayEquals(BASELINE_DIGEST.digest(), cryptoHash.apply((byte[]) null));
	}

	@Test
	@DisplayName("hash for list of CivitasBigintegers is correct")
	void test_list() {
		BASELINE_DIGEST.update(BIGINT_A.toByteArray());
		BASELINE_DIGEST.update(BIGINT_B.toByteArray());
		BASELINE_DIGEST.update(BIGINT_C.toByteArray());
		BASELINE_DIGEST.update(BIGINT_D.toByteArray());
		assertArrayEquals(BASELINE_DIGEST.digest(), cryptoHash.apply(List.of(BIGINT_A, BIGINT_B, BIGINT_C, BIGINT_D)));
	}

	@Test
	@DisplayName("hash for list of CivitasBigintegers is correct even if some of them is null")
	void test_list2() {
		BASELINE_DIGEST.update(BIGINT_A.toByteArray());
		BASELINE_DIGEST.update(BIGINT_C.toByteArray());
		BASELINE_DIGEST.update(BIGINT_D.toByteArray());

		assertArrayEquals(
				BASELINE_DIGEST.digest(),
				cryptoHash.apply(Stream.of(BIGINT_A, null, BIGINT_C, BIGINT_D).toList()));
	}

	@Test
	@Tag("testdata")
	@DisplayName("CURRENT_TIME")
	void test10() throws IOException {
		assertEquals(
				CURRENT_TIME,
				new DataInputStream(new ByteArrayInputStream(CURRENT_TIME_STRINGBASE.getBytes())).readLong());
	}

	@Test
	@DisplayName("hash for three bigintegers and an environment byte array is correct")
	void test8() {
		assertEquals(
				EL_GAMAL_SIGNED_CIPHERTEXT_C_BASE64,
				Base64.getEncoder()
						.encodeToString(cryptoHash
								.apply(
										EL_GAMAL_SIGNED_CIPHERTEXT_HASH1,
										EL_GAMAL_SIGNED_CIPHERTEXT_A,
										EL_GAMAL_SIGNED_CIPHERTEXT_B,
										ADDITIONALENV_BYTES)
								.toByteArray()));
	}

	@Test
	@DisplayName("hash for two bigintegers and an environment byte array is correct")
	void test9() {
		BASELINE_DIGEST.update(BIGINT_A.toByteArray());
		BASELINE_DIGEST.update(BIGINT_B.toByteArray());
		BASELINE_DIGEST.update(ADDITIONALENV_BYTES);

		assertEquals(
				CivitasBigIntegerFactory.obtain(BASELINE_DIGEST.digest()),
				cryptoHash.apply(BIGINT_A, BIGINT_B, null, ADDITIONALENV_BYTES));
	}
}
