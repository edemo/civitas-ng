package civitas.common.tests;

import static org.mockito.Mockito.mock;

import civitas.common.LoggerService;

public class LoggerServiceStub {
	public static LoggerService stub() {
		return mock(LoggerService.class);
	}
}
