package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PrivateKeyCTest extends ConcreteTestBase {

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PRIVATE_KEY_XML, new PrivateKeyC(PRIVATE_KEY).toXML());
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test1() throws Exception {
		assertEquals(PRIVATE_KEY_XML,
				((PrivateKeyC) PrivateKeyC.fromXML(new StringReader(PRIVATE_KEY_XML)))
						.toXML());
	}
}
