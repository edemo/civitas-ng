package civitas.crypto.sharedkey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

public class SharedKeyToWireTest extends TestBase implements SharedKeyTestData {

	@InjectMocks
	SharedKeyToWire sharedKeyToWire;

	@Test
	@DisplayName("toWire works as expected")
	void test2() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		sharedKeyToWire.apply(SHARED_KEY, pw);
		assertEquals(SHARED_KEY_ON_WIRE, sw.toString());
	}

}
