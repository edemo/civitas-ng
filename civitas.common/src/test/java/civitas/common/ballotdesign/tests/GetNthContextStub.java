package civitas.common.ballotdesign.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.ballotdesign.GetNthContext;
import civitas.common.election.tests.ElectionDetailsTestData;

public class GetNthContextStub implements ElectionDetailsTestData {
	public static GetNthContext stub() {
		GetNthContext mock = mock(GetNthContext.class);
		when(mock.apply(BALLOTDESIGN, 0)).thenReturn(BARE_CONTEXT_0);
		when(mock.apply(BALLOTDESIGN, 1)).thenReturn(BARE_CONTEXT_1);
		when(mock.apply(BALLOTDESIGN, 2)).thenReturn(BARE_CONTEXT_2);
		return mock;
	}
}
