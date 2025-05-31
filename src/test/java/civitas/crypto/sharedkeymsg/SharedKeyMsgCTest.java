package civitas.crypto.sharedkeymsg;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.BasicValuesTestData;

public class SharedKeyMsgCTest extends TestBase
		implements BasicValuesTestData {

	@Test
	@DisplayName("string based constructor and toBytes works as expected")
	void test1() {
		assertArrayEquals(BYTES, new SharedKeyMsg(SOMESTRING).m.getBytes());
	}

}
