package civitas.util.tests;

import java.util.Arrays;
import java.util.List;

import civitas.common.CommonUtil;
import civitas.crypto.Constants;
import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;

public interface BasicValuesTestData extends Constants {
	String VOTER_NAME = "bob";

	int BYTELENGTH = 8;
	int BITLENGTH = 64;
	int RANDOM_RUNS = 200;
	String CURRENT_TIME_STRINGBASE = "currentt";
	Long CURRENT_TIME = 7166760217683588212L;
	String SOMESTRING = "testdata";
	byte[] SOMESTRING_HASH = "somestring_hash".getBytes();
	String SOMESTRING_BASE64 = "dGVzdGRhdGE=";
	String SOMESTRING_EXTENDED = "exttestdatawas";
	byte[] BYTES = SOMESTRING.getBytes();
	CivitasBigInteger SOMESTRING_BIGINT = CivitasBigIntegerFactory.obtain(BYTES);
	int SOME_INT = 0xdeadbeef;
	int SOME_SMALL_INT = 0xbeef;
	CivitasBigInteger SOME_INT_BIG = CivitasBigIntegerFactory.obtain(SOME_INT);
	String ONE_BASE64 = "AQ==";
	long LONG = 0xdeadbeeff001be41L;
	byte[] LONG_AS_BYTES = {
		(byte) 0x41, (byte) 0xbe, (byte) 0x01, (byte) 0xf0, (byte) 0xef, (byte) 0xbe, (byte) 0xad, (byte) 0xde
	};
	List<String> RANDOMS_BASE64 = Arrays.asList(
			"JY+s34cV7ybeZLEpACLziJ3ZTb5fiCoGkx7duQSKvms=",
			"POILzE0M8iAYB1dCQW44j2oeofu6U7JMxnxoboocpBI=",
			"N8WtyRCrye3u8iDSDnKjAob9Wr/fMDqx3x3FE+OyDkM=",
			"FWuS+OV8XlmHhhSkQlmUEh8WqQKXtGe2ZXrCQYdlWSc=",
			"PhueZ8IkWuW1hKmKTu4HuVc/1Q17a8ECMrruNPcB5CE=",
			"FFSJ4i87Iay5ThFPoPGZJy/kjuvaiZOUJO/8msEvW18=",
			"QFudPIWYcJK7A5/z60VK9k8DAtdbrxBqRmN8tKY0LmM=",
			"aagoG+00Hkv6JKd+Eu36b71uiENK+JEYwkILYzcvPjA=",
			"J99xWmsnnK44p5jAneas1cCMpu2a8OaEce4xVlmYdyU=",
			"SpTLw0PIEN5xaJmNIgpUxxEx+fB//3IKTt84E+tZau8=",
			"ZV94rBMZC0PpdfHIyDfZn1+KfWA60pS4bXPcHLmj9bU=",
			"OKtYqfJ4vmY8tCO+RzZCbVXrShY/empz4LDoeR3TRLE=",
			"Hq9Xnlqud+A9w1RgrOZwAJczXRDGjb6ato9In07zTbE=",
			"AyROZOzkB5ZvSYRZY/gaEwSHHUDZnP3DGvRwKbvsLko=",
			"WwbZnOX/5sFBACtHWa1wY79m0wdqNodnEEYViQO60CA=",
			"JNY1zRo/beagsCVWQ0Pq/Z3bwjbnsNWj9za7r+Sp5Ig=",
			"ETt2DOX9cp7rrtMhB0eKd+YrxX2r4UA1A+qpTB4K6HU=",
			"WEVwfWwFRfiv54k2w4mwtYGXPLSTVqdILmSVV58aN8Y=",
			"aranQU18SXHbB4kpq3XRty8Vhrj6HWQE7/MI6PHP8iA=",
			"XQZehe2oHfhwUPncTZeIjptKAxJpTir/qDkAOmrw0S4=",
			"Kf9BasH/sfW3/d/PG3hORmLgDaCPSW8rZfpuRhctJQg=",
			"Mu2nitUW8QuFmYtBKMUi0Wv7CsE+fmR+BOfhi4caUBc=",
			"ELWfG0ilnQMt9gFhHDAjKv0NTs/B9JL3uEgPdjHZLDU=",
			"To9ORGbDW1mqtRnk75VcfmwjPUEfLiUzf02cQ8sd/ic=",
			"DAp/Il7x/G3hUMayiXTpJadDL5RQ9aE72uHLEZVD8b8=",
			"D19SgAbF8qYqyU70ivnyDzerrCrjcZQUJKR1UIjhpjs=",
			"WhWDQ0QNB6P8i8XyQyWVQl4XLE1Z6H1bwTMjxmOuCKk=",
			"Y68p7FIkSehbsEy9DEFm4ZCLBNHWvOHfZAFiJcNNyYU=",
			"KnJKpa62Fm1sRo+qU1RfkGD6heTRBoEgkdL5CIFHKUk=");

	List<CivitasBigInteger> RANDOMS =
			RANDOMS_BASE64.stream().map(CommonUtil::asBigint).toList();

	CivitasBigInteger RANDOMS_0 = RANDOMS.getFirst();
	String RANDOMS_0_BASE64 = RANDOMS_BASE64.getFirst();

	CivitasBigInteger RANDOMS_1 = RANDOMS.get(1);
	String RANDOMS_1_BASE64 = RANDOMS_BASE64.get(1);

	CivitasBigInteger RANDOMS_2 = RANDOMS.get(2);
	String RANDOMS_2_BASE64 = RANDOMS_BASE64.get(2);

	String BIGINT_A_BASE64 = SOMESTRING_BASE64;
	String SAFE_P_MINUS_A_BASE64 =
			"ASvIFyEHZA21reK32LTtYEwJG4GSW8Xzrl83llQSdjcztgsIJeyQm0ZZ36vG4aTVYjtADzV9J6xJcK0C32+iCP4=";
	CivitasBigInteger SAFE_P_MINUS_A = CommonUtil.asBigint(SAFE_P_MINUS_A_BASE64);

	String BIGINT_B_BASE64 = "Xje5W2KfxNk=";
	String BIGINT_C_BASE64 = "eaUSELMHNaE=";

	String BIGINT_D_BASE64 = "eOmnTcKMIpE=";

	CivitasBigInteger BIGINT_A = CivitasBigIntegerFactory.obtain(SOMESTRING.getBytes());
	CivitasBigInteger BIGINT_B = CommonUtil.asBigint(BIGINT_B_BASE64);
	CivitasBigInteger BIGINT_C = CommonUtil.asBigint(BIGINT_C_BASE64);
	CivitasBigInteger BIGINT_D = CommonUtil.asBigint(BIGINT_D_BASE64);
	String AUTHENTICATION_NONCE = "auth nonce";

	int[] INTEGER_PERMUTATION = {3, 0, 1, 2, 4};
}
