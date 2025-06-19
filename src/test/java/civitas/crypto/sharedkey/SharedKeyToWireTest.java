package civitas.crypto.sharedkey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;

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
public class SharedKeyToWireTest extends TestBase implements SharedKeyTestData {

	@Autowired
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
