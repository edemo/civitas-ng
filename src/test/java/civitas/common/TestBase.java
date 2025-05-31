package civitas.common;

import org.junit.jupiter.api.BeforeEach;

import civitas.util.DI;

public class TestBase {

	@BeforeEach
	public void setUp() {
		DI.stubUp(this);
		DI.fill(this);
		RandomAnswer.step = 0;

	}

}