package civitas.crypto.sharedkey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

public class SharedKeyCTest extends TestBase implements SharedKeyTestData {

	@Use
	SharedKeyFromWire sharedKeyFromWire;
	@Use
	SharedKeyFromXML sharedKeyFromXML;
	@Use
	SharedKeyToWire sharedKeyToWire;
	@Use
	SharedKeyToXML sharedKeyToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(SHARED_KEY_XML, sharedKeyToXML.apply(SHARED_KEY));
	}

	@Test
	@DisplayName("toWire works as expected")
	void test2() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		sharedKeyToWire.apply(SHARED_KEY, pw);
		assertEquals(SHARED_KEY_ON_WIRE, sw.toString());
	}

	@Test
	@DisplayName("fromWire works as expected")
	void test3() throws IllegalArgumentException, IOException {
		StringReader sr = new StringReader(SHARED_KEY_ON_WIRE);
		BufferedReader br = new BufferedReader(sr);
		SharedKeyC fromWire = (SharedKeyC) sharedKeyFromWire.apply(br);
		assertEquals(SHARED_KEY, fromWire);
	}

	@Test
	@DisplayName("name returns the key name")
	void test4() {
		assertEquals(SHARED_KEY_NAME, SHARED_KEY.name);
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test1() throws IllegalArgumentException, IOException {
		assertEquals(SHARED_KEY,
				(sharedKeyFromXML.apply(new StringReader(SHARED_KEY_XML))));
	}

}
