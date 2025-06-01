package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

public class SetUpDecodeMapTest extends TestBase
		implements ElGamalParametersTestData {

	@Tested
	SetUpDecodeMap setUpDecodeMap;

	@Test
	@DisplayName("sets up a decode map for the choice values")
	void test() {
		assertEquals(DECODEMAP, setUpDecodeMap.apply(CHOICES, EL_GAMAL_PARAMETERS));
	}
}
