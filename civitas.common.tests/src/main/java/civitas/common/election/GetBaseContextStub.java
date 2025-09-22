package civitas.common.election;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetBaseContextStub implements ElectionDetailsTestData {

	public static GetBaseContext stub() {
		GetBaseContext mock = mock(GetBaseContext.class);
		when(mock.apply(ELECTION_DETAILS, 14)).thenReturn(ELECTION_ID_STRING + ":14:");
		when(mock.apply(ELECTION_DETAILS, 11)).thenReturn(ELECTION_ID_STRING + ":11:");
		when(mock.apply(ELECTION_DETAILS, 3)).thenReturn(ELECTION_ID_STRING + ":3:");
		when(mock.apply(ELECTION_DETAILS, 4)).thenReturn(ELECTION_ID_STRING + ":4:");
		return mock;
	}
}
