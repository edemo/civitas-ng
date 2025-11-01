package civitas.util.tests;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.util.InitialTests;

class InitialTestsTest {

	@Test
	@DisplayName("make sure that the initial tests run okay")
	void test() throws UnsupportedEncodingException {
		InitialTests.doTests();
	}
}
