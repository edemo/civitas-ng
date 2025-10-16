package civitas.crypto.publickey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import io.github.magwas.testing.TestBase;

class GetElGamalPublicKeyNameTest extends TestBase implements ElGamalPublicKeyTestData {

	@InjectMocks
	GetElGamalPublicKeyName getElGamalPublicKeyName;

	@Test
	@DisplayName("name returns a name")
	void test2() {
		assertEquals(EL_GAMALPUBLIC_KEY_NAME, getElGamalPublicKeyName.apply(EL_GAMAL_PUBLIC_KEY_E));
	}
}
