package civitas.crypto.publickeymsg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.BasicValuesTestData;
import civitas.crypto.ConcreteTestBase;
import civitas.crypto.msg.PublicKeyMsgC;

public class PublicKeyMsgCTest extends ConcreteTestBase
		implements BasicValuesTestData {

	@Test
	@DisplayName("can be instantiated with a byte array")
	void test() {
		assertEquals(new PublicKeyMsgC(SOMESTRING).m,
				new PublicKeyMsgC(SOMESTRING.getBytes()).m);
	}
}
