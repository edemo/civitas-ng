package civitas.crypto.sharedkey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.util.BasicValuesTestData;

class GenerateSharedKeyTest extends RandomAwareTestBase implements SharedKeyTestData, BasicValuesTestData {

	@InjectMocks
	GenerateSharedKey generateSharedKey;

	@Test
	void test() {
		assertEquals(SHARED_KEY, generateSharedKey.apply(SHARED_KEY_SIZE));
	}
}
