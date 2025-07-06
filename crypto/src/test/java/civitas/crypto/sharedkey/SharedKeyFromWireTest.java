package civitas.crypto.sharedkey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

public class SharedKeyFromWireTest extends TestBase
		implements SharedKeyTestData {

	@InjectMocks
	SharedKeyFromWire sharedKeyFromWire;

	@Test
	@DisplayName("fromWire works as expected")
	void test3() throws IllegalArgumentException, IOException {
		StringReader sr = new StringReader(SHARED_KEY_ON_WIRE);
		BufferedReader br = new BufferedReader(sr);
		SharedKey fromWire = sharedKeyFromWire.apply(br);
		assertEquals(SHARED_KEY, fromWire);
	}

}
