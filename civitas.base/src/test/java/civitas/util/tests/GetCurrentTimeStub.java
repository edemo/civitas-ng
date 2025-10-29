package civitas.util.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.util.GetCurrentTime;

public class GetCurrentTimeStub implements BasicValuesTestData {
	public static GetCurrentTime stub() {
		GetCurrentTime mock = mock(GetCurrentTime.class);
		when(mock.apply()).thenReturn(CURRENT_TIME);
		return mock;
	}
}
