package civitas.crypto.rsaprivatekey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.util.Use;

public class PrivateKeyCTest extends ConcreteTestBase
		implements PrivateKeyTestData {
	@Use
	PrivateKeyFromXML privateKeyFromXML;
	@Use
	PrivateKeyToXML privateKeyToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PRIVATE_KEY_XML, privateKeyToXML.apply(PRIVATE_KEY));
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test1() throws Exception {
		assertEquals(PRIVATE_KEY, privateKeyFromXML
				.apply(new StringReader(PRIVATE_KEY_XML)));
	}
}
