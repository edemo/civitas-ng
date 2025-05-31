package civitas.crypto.sharedkeymsg;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.BasicValuesTestData;
import civitas.crypto.ConcreteTestBase;

public class SharedKeyMsgCTest extends ConcreteTestBase
		implements BasicValuesTestData {

	@Test
	@DisplayName("string based constructor and toBytes works as expected")
	void test1() {
		assertArrayEquals(BYTES, new SharedKeyMsg(SOMESTRING).m.getBytes());
	}

}
