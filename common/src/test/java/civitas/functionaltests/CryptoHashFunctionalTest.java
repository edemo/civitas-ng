package civitas.functionaltests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.bboard.common.BBPostTestData;
import civitas.common.TestBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.messagedigest.MessageDigestTestData;
import civitas.crypto.signature.SignatureTestData;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertextTestData;

@Tag("functional")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
public class CryptoHashFunctionalTest extends TestBase
		implements MessageDigestTestData, ElGamalSignedCiphertextTestData,
		SignatureTestData, BBPostTestData {

	@Autowired
	CryptoHash cryptoHash;

	@Test
	@DisplayName("digest for just initialized one is correct")
	void test() {
		assertArrayEquals(BASELINE_DIGEST.digest(),
				cryptoHash.apply(new byte[] {}));
	}

	@Test
	@DisplayName("digest for byte array is correct")
	void test1() {
		BASELINE_DIGEST.update(BYTES);
		assertArrayEquals(BASELINE_DIGEST.digest(), cryptoHash.apply(BYTES));
	}

	@Test
	@DisplayName("if updated with (byte[]) null, nothing happens")
	void test2() {
		assertArrayEquals(BASELINE_DIGEST.digest(),
				cryptoHash.apply((byte[]) null));
	}

	@Test
	@DisplayName("if updated with (String) null, nothing happens")
	void test2_2() throws UnsupportedEncodingException {
		assertArrayEquals(BASELINE_DIGEST.digest(),
				cryptoHash.apply((String) null));
	}

	@Test
	@DisplayName("digest for byte is correct")
	void test3() {
		BASELINE_DIGEST.update((byte) 42);
		assertArrayEquals(BASELINE_DIGEST.digest(), cryptoHash.apply((byte) 42));
	}

	@Test
	@DisplayName("digest for int is correct")
	void test4() {

		BASELINE_DIGEST.update(
				new byte[] { (byte) 0xef, (byte) 0xbe, (byte) 0xad, (byte) 0xde });
		assertArrayEquals(BASELINE_DIGEST.digest(), cryptoHash.apply(0xdeadbeef));
	}

	@Test
	@DisplayName("digest for long is correct")
	void test5() {
		BASELINE_DIGEST.update(CURRENT_TIME_STRINGBASE.getBytes());
		assertArrayEquals(BASELINE_DIGEST.digest(), cryptoHash.apply(CURRENT_TIME));
	}

	@Test
	@DisplayName("digest for String is correct")
	void test6() throws UnsupportedEncodingException {
		BASELINE_DIGEST.update(BYTES);
		assertArrayEquals(BASELINE_DIGEST.digest(), cryptoHash.apply(SOMESTRING));
	}

	@Test
	@DisplayName("digest for char array with offset is correct")
	void test7() throws UnsupportedEncodingException {
		BASELINE_DIGEST.update(BYTES);
		assertArrayEquals(BASELINE_DIGEST.digest(),
				cryptoHash.apply(SOMESTRING_EXTENDED.toCharArray(), 3, 8));
	}

	@Test
	@DisplayName("digest for hash, time and signature is correct")
	void test9() {
		byte[] bytes = (BBPOST_HASH_STRINGBASE + CURRENT_TIME_STRINGBASE
				+ BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE_STRINGBASE).getBytes();
		BASELINE_DIGEST.update(bytes);
		byte[] digest = BASELINE_DIGEST.digest();

		assertArrayEquals(digest, cryptoHash.apply(BBPOST, CURRENT_TIME,
				BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE));
	}

	@Test
	@Tag("testdata")
	@DisplayName("CURRENT_TIME")
	void test10() throws IOException {
		assertEquals(CURRENT_TIME,
				new DataInputStream(
						new ByteArrayInputStream(CURRENT_TIME_STRINGBASE.getBytes()))
						.readLong());
	}

	@Test
	@Tag("testdata")
	@DisplayName("EL_GAMAL_SIGNED_CIPHERTEXT_C_BASE64")
	void test8() {
		assertEquals(EL_GAMAL_SIGNED_CIPHERTEXT_C_BASE64, Base64.getEncoder()
				.encodeToString(cryptoHash.apply(EL_GAMAL_SIGNED_CIPHERTEXT_HASH1,
						EL_GAMAL_SIGNED_CIPHERTEXT_A, EL_GAMAL_SIGNED_CIPHERTEXT_B,
						ADDITIONALENV_BYTES).toByteArray()));
	}

}
