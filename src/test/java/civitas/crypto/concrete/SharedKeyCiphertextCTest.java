package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SharedKeyCiphertextCTest extends ConcreteTestBase
		implements ConcreteTestData {

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		SharedKeyCiphertextC t = new SharedKeyCiphertextC(BYTES);
		assertEquals(SHARED_KEY_CIPHERTEXT_XML, t.toXML());
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test1() throws IllegalArgumentException, IOException {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		SharedKeyCiphertextC.fromXML(new StringReader(SHARED_KEY_CIPHERTEXT_XML))
				.toXML(printWriter);
		assertEquals(SHARED_KEY_CIPHERTEXT_XML, stringWriter.toString());
	}

	@Test
	@DisplayName("toBytes returns the bytes")
	void test2() {
		byte[] bytes2 = BIGINT_D_BASE64.getBytes();
		SharedKeyCiphertextC t = new SharedKeyCiphertextC(bytes2);
		assertArrayEquals(bytes2, t.toBytes());
	}

}
