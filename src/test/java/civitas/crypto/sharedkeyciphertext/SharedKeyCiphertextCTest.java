package civitas.crypto.sharedkeyciphertext;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.ciphertext.ElGamalCiphertextCTestData;
import civitas.crypto.signedciphertext.SharedKeyCiphertextFromXML;
import civitas.util.Use;

public class SharedKeyCiphertextCTest extends ConcreteTestBase
		implements ElGamalCiphertextCTestData {
	@Use
	SharedKeyCiphertextFromXML sharedKeyCiphertextFromXML;
	@Use
	SharedKeyCiphertextToXML sharedKeyCiphertextToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		SharedKeyCiphertext t = new SharedKeyCiphertext(BYTES);
		assertEquals(SHARED_KEY_CIPHERTEXT_XML, sharedKeyCiphertextToXML.apply(t));
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test1() throws IllegalArgumentException, IOException {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		sharedKeyCiphertextToXML.apply(sharedKeyCiphertextFromXML
				.apply(new StringReader(SHARED_KEY_CIPHERTEXT_XML)), printWriter);
		assertEquals(SHARED_KEY_CIPHERTEXT_XML, stringWriter.toString());
	}

	@Test
	@DisplayName("toBytes returns the bytes")
	void test2() {
		byte[] bytes2 = BIGINT_D_BASE64.getBytes();
		SharedKeyCiphertext t = new SharedKeyCiphertext(bytes2);
		assertArrayEquals(bytes2, t.encryptedBytes);
	}

}
