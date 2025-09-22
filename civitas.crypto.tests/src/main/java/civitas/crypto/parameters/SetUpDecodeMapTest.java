package civitas.crypto.parameters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import io.github.magwas.testing.TestBase;

class SetUpDecodeMapTest extends TestBase implements ElGamalParametersTestData {

	@InjectMocks
	SetUpDecodeMap setUpDecodeMap;

	@Test
	@DisplayName("sets up a decode map for the choice values")
	void test() {
		assertEquals(DECODEMAP, setUpDecodeMap.apply(CHOICES, EL_GAMAL_PARAMETERS));
	}
}
