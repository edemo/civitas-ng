package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MessageDigestCTest {

	private static final String TESTDATA_EXTENDED = "exttestdatawas";
	private static final String TESTDATA = "testdata";
	private static final byte[] BYTES = TESTDATA.getBytes();
	private MessageDigestC md;
	private java.security.MessageDigest baselineDigest;

	@BeforeEach
	void setUp() throws NoSuchAlgorithmException {
		java.security.MessageDigest messageDigest = java.security.MessageDigest
				.getInstance("SHA-256");
		baselineDigest = java.security.MessageDigest.getInstance("SHA-256");
		md = new MessageDigestC(messageDigest);

	}

	@Test
	@DisplayName("digest for just initialized one is correct")
	void test() {
		assertArrayEquals(baselineDigest.digest(), md.digest());
	}

	@Test
	@DisplayName("digest for byte array is correct")
	void test1() {
		md.update(BYTES);
		baselineDigest.update(BYTES);
		assertArrayEquals(baselineDigest.digest(), md.digest());
	}

	@Test
	@DisplayName("if updated with (byte[]) null, nothing happens")
	void test2() {
		md.update((byte[]) null);
		assertArrayEquals(baselineDigest.digest(), md.digest());
	}

	@Test
	@DisplayName("if updated with (String) null, nothing happens")
	void test2_2() {
		md.update((String) null);
		assertArrayEquals(baselineDigest.digest(), md.digest());
	}

	@Test
	@DisplayName("digest for byte is correct")
	void test3() {
		md.update((byte) 42);
		baselineDigest.update((byte) 42);
		assertArrayEquals(baselineDigest.digest(), md.digest());
	}

	@Test
	@DisplayName("digest for int is correct")
	void test4() {
		md.update(0xdeadbeef);
		baselineDigest.update(
				new byte[] { (byte) 0xef, (byte) 0xbe, (byte) 0xad, (byte) 0xde });
		assertArrayEquals(baselineDigest.digest(), md.digest());
	}

	@Test
	@DisplayName("digest for long is correct")
	void test5() {
		md.update(0xdeadbeeff001be41L);
		baselineDigest.update(new byte[] {
				(byte) 0x41,
				(byte) 0xbe,
				(byte) 0x01,
				(byte) 0xf0,
				(byte) 0xef,
				(byte) 0xbe,
				(byte) 0xad,
				(byte) 0xde });
		assertArrayEquals(baselineDigest.digest(), md.digest());
	}

	@Test
	@DisplayName("digest for String is correct")
	void test6() {
		md.update(TESTDATA);
		baselineDigest.update(BYTES);
		assertArrayEquals(baselineDigest.digest(), md.digest());
	}

	@Test
	@DisplayName("digest for char array with offset is correct")
	void test7() {
		char[] ca = TESTDATA_EXTENDED.toCharArray();
		md.update(ca, 3, 8);
		baselineDigest.update(BYTES);
		assertArrayEquals(baselineDigest.digest(), md.digest());
	}

}
