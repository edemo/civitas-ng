package civitas.crypto.ciphertextlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

public class CiphertextListTest extends TestBase
		implements ElGamalCiphertextListTestData {
	@InjectMocks
	ConstructCiphertextList constructCiphertextList;

	@Test
	@DisplayName("get(n) gets the nth cypertext")
	void test3() {
		assertEquals(CIPHERTEXT_E, CIPHERTEXTLIST_ONEINSIDE.get(0));
	}

	@Test
	@DisplayName("get(n) throws IndexOutOfBoundsException for out of bounds index")
	void test4() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> CIPHERTEXTLIST_ONEINSIDE.get(1));
	}

}
