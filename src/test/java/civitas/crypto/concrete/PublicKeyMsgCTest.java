package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PublicKeyMsgCTest extends ConcreteTestBase {

	@Test
	@DisplayName("can be instantiated with a byte array")
	void test() {
		assertEquals(new PublicKeyMsgC(SOMESTRING).m,
				new PublicKeyMsgC(SOMESTRING.getBytes()).m);
	}
}
