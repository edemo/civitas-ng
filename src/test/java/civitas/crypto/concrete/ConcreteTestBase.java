package civitas.crypto.concrete;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;

import civitas.common.RandomAnswer;
import civitas.crypto.CryptoException;
import civitas.util.DI;

public class ConcreteTestBase {

	@BeforeEach
	void setUp() throws NoSuchAlgorithmException, IllegalArgumentException,
			IOException, CryptoException {
		DI.stubUp(this);
		DI.fill(this);
		RandomAnswer.step = 0;

	}

}