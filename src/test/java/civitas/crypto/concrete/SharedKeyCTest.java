package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SharedKeyCTest extends ConcreteTestBase
		implements ConcreteTestData {

	SharedKeyC SHARED_KEY_C = new SharedKeyC(
			new SecretKeySpec(Base64.getDecoder().decode(SHARED_KEY_BASE64), "AES"),
			SHARED_KEY_NAME);

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(SHARED_KEY_XML, SHARED_KEY_C.toXML());
	}

	@Test
	@DisplayName("toWire works as expected")
	void test2() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		SHARED_KEY_C.toWire(pw);
		assertEquals(SHARED_KEY_ON_WIRE, sw.toString());
	}

	@Test
	@DisplayName("fromWire works as expected")
	void test3() throws IllegalArgumentException, IOException {
		StringReader sr = new StringReader(SHARED_KEY_ON_WIRE);
		BufferedReader br = new BufferedReader(sr);
		SharedKeyC fromWire = (SharedKeyC) SharedKeyC.fromWire(br);
		assertEquals(SHARED_KEY_XML, fromWire.toXML());
	}

	@Test
	@DisplayName("name returns the key name")
	void test4() {
		assertEquals(SHARED_KEY_NAME, SHARED_KEY_C.name());
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test1() throws IllegalArgumentException, IOException {
		assertEquals(SHARED_KEY_XML,
				((SharedKeyC) SharedKeyC.fromXML(new StringReader(SHARED_KEY_XML)))
						.toXML());
	}

}
