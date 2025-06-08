package civitas.common.ballotdesign;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatePositionInBallotStub {
	public static CalculatePositionInBallot stub() {
		CalculatePositionInBallot mock = mock(CalculatePositionInBallot.class);
		when(mock.apply(0, 1, 3)).thenReturn(0);
		when(mock.apply(0, 2, 3)).thenReturn(1);
		when(mock.apply(1, 2, 3)).thenReturn(2);
		return mock;
	}
}
