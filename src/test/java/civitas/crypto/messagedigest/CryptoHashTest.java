package civitas.crypto.messagedigest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.common.TestUtil;

public class CryptoHashTest extends TestBase implements MessageDigestTestData {

	MessageDigest md = new MessageDigest(TestUtil.getBaselineDigest());
	@InjectMocks
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
	void test2_2() {
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
		BASELINE_DIGEST.update(LONG_AS_BYTES);
		assertArrayEquals(BASELINE_DIGEST.digest(), cryptoHash.apply(LONG));
	}

	@Test
	@DisplayName("digest for String is correct")
	void test6() {
		BASELINE_DIGEST.update(BYTES);
		assertArrayEquals(BASELINE_DIGEST.digest(), cryptoHash.apply(SOMESTRING));
	}

	@Test
	@DisplayName("digest for char array with offset is correct")
	void test7() {
		BASELINE_DIGEST.update(BYTES);
		assertArrayEquals(BASELINE_DIGEST.digest(),
				cryptoHash.apply(SOMESTRING_EXTENDED.toCharArray(), 3, 8));
	}

}
