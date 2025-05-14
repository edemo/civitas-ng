package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SharedKeyMsgCTest extends ConcreteTestBase {

	@Test
	@DisplayName("byte array based constructor and toString works as expected")
	void test() {
		assertEquals(SOMESTRING, new SharedKeyMsgC(BYTES).toString());
	}

	@Test
	@DisplayName("string based constructor and toBytes works as expected")
	void test1() {
		assertArrayEquals(BYTES, new SharedKeyMsgC(SOMESTRING).toBytes());
	}

}
