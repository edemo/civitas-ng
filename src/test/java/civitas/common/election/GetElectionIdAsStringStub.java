package civitas.common.election;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetElectionIdAsStringStub implements ElectionTestData {
	public static GetElectionIdAsString stub() {
		GetElectionIdAsString mock = mock(GetElectionIdAsString.class);
		when(mock.apply(ELECTION_ID)).thenReturn(ELECTION_ID_AS_STRING);
		return mock;
	}
}
