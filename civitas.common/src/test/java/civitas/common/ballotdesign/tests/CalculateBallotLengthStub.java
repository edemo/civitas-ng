package civitas.common.ballotdesign.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.ballotdesign.CalculateBallotLength;

public class CalculateBallotLengthStub {
	public static CalculateBallotLength stub() {
		CalculateBallotLength mock = mock(CalculateBallotLength.class);
		when(mock.apply(3)).thenReturn(3);
		when(mock.apply(2)).thenReturn(1);
		when(mock.apply(4)).thenReturn(6);
		return mock;
	}
}
