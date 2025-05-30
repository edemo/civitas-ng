package civitas.crypto.messagedigest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestUtil;
import civitas.crypto.ConcreteTestBase;

public class MessageDigestCTest extends ConcreteTestBase
		implements MessageDigestTestData {

	MessageDigestC md = new MessageDigestC(TestUtil.getBaselineDigest());

	@Test
	@DisplayName("digest for just initialized one is correct")
	void test() {
		assertArrayEquals(BASELINE_DIGEST.digest(), md.digest());
	}

	@Test
	@DisplayName("digest for byte array is correct")
	void test1() {
		md.update(BYTES);
		BASELINE_DIGEST.update(BYTES);
		assertArrayEquals(BASELINE_DIGEST.digest(), md.digest());
	}

	@Test
	@DisplayName("if updated with (byte[]) null, nothing happens")
	void test2() {
		md.update((byte[]) null);
		assertArrayEquals(BASELINE_DIGEST.digest(), md.digest());
	}

	@Test
	@DisplayName("if updated with (String) null, nothing happens")
	void test2_2() {
		md.update((String) null);
		assertArrayEquals(BASELINE_DIGEST.digest(), md.digest());
	}

	@Test
	@DisplayName("digest for byte is correct")
	void test3() {
		md.update((byte) 42);
		BASELINE_DIGEST.update((byte) 42);
		assertArrayEquals(BASELINE_DIGEST.digest(), md.digest());
	}

	@Test
	@DisplayName("digest for int is correct")
	void test4() {
		md.update(0xdeadbeef);
		BASELINE_DIGEST.update(
				new byte[] { (byte) 0xef, (byte) 0xbe, (byte) 0xad, (byte) 0xde });
		assertArrayEquals(BASELINE_DIGEST.digest(), md.digest());
	}

	@Test
	@DisplayName("digest for long is correct")
	void test5() {
		md.update(LONG);
		BASELINE_DIGEST.update(LONG_AS_BYTES);
		assertArrayEquals(BASELINE_DIGEST.digest(), md.digest());
	}

	@Test
	@DisplayName("digest for String is correct")
	void test6() {
		md.update(SOMESTRING);
		BASELINE_DIGEST.update(BYTES);
		assertArrayEquals(BASELINE_DIGEST.digest(), md.digest());
	}

	@Test
	@DisplayName("digest for char array with offset is correct")
	void test7() {
		char[] ca = SOMESTRING_EXTENDED.toCharArray();
		md.update(ca, 3, 8);
		BASELINE_DIGEST.update(BYTES);
		assertArrayEquals(BASELINE_DIGEST.digest(), md.digest());
	}

}
