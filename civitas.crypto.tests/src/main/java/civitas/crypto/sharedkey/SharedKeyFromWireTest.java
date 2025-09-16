package civitas.crypto.sharedkey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import io.github.magwas.testing.TestBase;

class SharedKeyFromWireTest extends TestBase
		implements SharedKeyTestData {

	@InjectMocks
	SharedKeyFromWire sharedKeyFromWire;

	@Test
	@DisplayName("fromWire works as expected")
	void test3() throws IllegalArgumentException, IOException {
		try (BufferedReader br = new BufferedReader(
				new StringReader(SHARED_KEY_ON_WIRE))) {
			SharedKey fromWire = sharedKeyFromWire.apply(br);
			assertEquals(SHARED_KEY, fromWire);
		}
	}

}
