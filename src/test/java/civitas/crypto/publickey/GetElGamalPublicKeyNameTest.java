package civitas.crypto.publickey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

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
public class GetElGamalPublicKeyNameTest extends TestBase
		implements ElGamalPublicKeyTestData {

	@Autowired
	GetElGamalPublicKeyName getElGamalPublicKeyName;

	@Test
	@DisplayName("name returns a name")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(EL_GAMALPUBLIC_KEY_NAME,
				getElGamalPublicKeyName.apply(EL_GAMAL_PUBLIC_KEY_E));
	}

}
