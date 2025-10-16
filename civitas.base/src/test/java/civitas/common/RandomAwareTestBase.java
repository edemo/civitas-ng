package civitas.common;

import org.junit.jupiter.api.BeforeEach;

import io.github.magwas.testing.TestBase;

public class RandomAwareTestBase extends TestBase {
	public static EnvironmentState state;

	@BeforeEach
	@Override
	public void setUp() throws Throwable {
		super.setUp();
		state = EnvironmentState.NORMAL;
		RandomAnswer.step = 0;
	}

	protected void given(final EnvironmentState newState) {
		state = newState;
	}
}
