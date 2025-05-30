package civitas.crypto;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;

import civitas.common.RandomAnswer;
import civitas.util.DI;

public class ConcreteTestBase {

	@BeforeEach
	public void setUp() throws NoSuchAlgorithmException, IllegalArgumentException,
			IOException, CryptoException {
		DI.stubUp(this);
		DI.fill(this);
		RandomAnswer.step = 0;

	}

}