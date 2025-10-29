package civitas.crypto.parameters.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.parameters.SetUpDecodeMap;
import io.github.magwas.konveyor.testing.TestBase;

class SetUpDecodeMapTest extends TestBase implements ElGamalParametersTestData {

	@InjectMocks
	SetUpDecodeMap setUpDecodeMap;

	@Test
	@DisplayName("sets up a decode map for the choice values")
	void test() {
		assertEquals(DECODEMAP, setUpDecodeMap.apply(CHOICES, EL_GAMAL_PARAMETERS));
	}
}
