package civitas.crypto.sharedkey.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.sharedkey.GenerateSharedKey;
import civitas.util.tests.BasicValuesTestData;

class GenerateSharedKeyTest extends RandomAwareTestBase implements SharedKeyTestData, BasicValuesTestData {

	@InjectMocks
	GenerateSharedKey generateSharedKey;

	@Test
	void test() {
		assertEquals(SHARED_KEY, generateSharedKey.apply(SHARED_KEY_SIZE));
	}
}
