package civitas.crypto.sharedkey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.common.TestBase;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
public class SharedKeyFromWireTest extends TestBase
		implements SharedKeyTestData {

	@Autowired
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
