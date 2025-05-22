package civitas.crypto.concrete;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import civitas.common.TestUtil;
import civitas.util.CivitasBigInteger;

public interface BasicValuesTestData {
	public static final String SOMESTRING = "testdata";
	public static final String SOMESTRING_BASE64 = "dGVzdGRhdGE=";
	public static final String SOMESTRING_EXTENDED = "exttestdatawas";
	public static final byte[] BYTES = SOMESTRING.getBytes();
	public static final int SOME_INT = 0xdeadbeef;
	public static final int SOME_SMALL_INT = 0xbeef;
	public static final CivitasBigInteger SOME_INT_BIG = CivitasBigInteger
			.valueOf(SOME_INT);
	public static final String ONE_BASE64 = "AQ==";
	public static final long LONG = 0xdeadbeeff001be41L;
	public static final byte[] LONG_AS_BYTES = new byte[] {
			(byte) 0x41,
			(byte) 0xbe,
			(byte) 0x01,
			(byte) 0xf0,
			(byte) 0xef,
			(byte) 0xbe,
			(byte) 0xad,
			(byte) 0xde };

	public static final List<String> RANDOMS_BASE64 = Arrays.asList(
			"JY+s34cV7ybeZLEpACLziJ3ZTb5fiCoGkx7duQSKvms=",
			"POILzE0M8iAYB1dCQW44j2oeofu6U7JMxnxoboocpBI=",
			"N8WtyRCrye3u8iDSDnKjAob9Wr/fMDqx3x3FE+OyDkM=",
			"AI6ANU43FzgU0jNSTA/2scyoriAcKi+nHxPt1XBf8i9w",
			"FWuS+OV8XlmHhhSkQlmUEh8WqQKXtGe2ZXrCQYdlWSc=");

	public static final List<CivitasBigInteger> RANDOMS = RANDOMS_BASE64.stream()
			.map((s) -> TestUtil.asBigint(s)).collect(Collectors.toList());

	public static final CivitasBigInteger RANDOMS_0 = RANDOMS.get(0);
	public static final String RANDOMS_0_BASE64 = RANDOMS_BASE64.get(0);

	public static final CivitasBigInteger RANDOMS_1 = RANDOMS.get(1);
	public static final String RANDOMS_1_BASE64 = RANDOMS_BASE64.get(1);

	public static final CivitasBigInteger RANDOMS_2 = RANDOMS.get(2);
	public static final String RANDOMS_2_BASE64 = RANDOMS_BASE64.get(2);

	public static final CivitasBigInteger BIGINT_A = new CivitasBigInteger(
			SOMESTRING.getBytes());
	public static final String BIGINT_A_BASE64 = SOMESTRING_BASE64;
	public static final String BIGINT_A_ENCRYPTED_SAFE_BASE64 = "ASvIFyEHZA21reK32LTtYEwJG4GSW8Xzrl83llQSdjcztgsIJeyQm0ZZ36vG4aTVYjtADzV9J6xJcK0C32+iCP4=";
	public static final CivitasBigInteger BIGINT_A_ENCRYPTED_SAFE = TestUtil
			.asBigint(BIGINT_A_ENCRYPTED_SAFE_BASE64);

	public static final String BIGINT_B_BASE64 = "Xje5W2KfxNk=";
	public static final CivitasBigInteger BIGINT_B = TestUtil
			.asBigint(BIGINT_B_BASE64);
	public static final String BIGINT_C_BASE64 = "eaUSELMHNaE=";
	public static final CivitasBigInteger BIGINT_C = TestUtil
			.asBigint(BIGINT_C_BASE64);

	public static final String BIGINT_D_BASE64 = "eOmnTcKMIpE=";
	public static final CivitasBigInteger BIGINT_D = TestUtil
			.asBigint(BIGINT_D_BASE64);

}
