package civitas.crypto.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoError;

class CryptoErrorTest {

	@Test
	@DisplayName("can be instantiated with a message")
	void test() {
		new CryptoError("err");
	}

	@Test
	@DisplayName("can be instantiated with a message and throwable")
	void test1() {
		new CryptoError("err", new Error());
	}

	@Test
	@DisplayName("can be instantiated with a throwable")
	void test2() {
		new CryptoError(new Error());
	}
}
