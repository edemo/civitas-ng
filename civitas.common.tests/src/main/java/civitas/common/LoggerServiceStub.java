package civitas.common;

import static org.mockito.Mockito.mock;

public class LoggerServiceStub {
	public static LoggerService stub() {
		LoggerService mock = mock(LoggerService.class);
		return mock;
	}
}
