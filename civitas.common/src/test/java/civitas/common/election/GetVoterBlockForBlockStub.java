package civitas.common.election;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetVoterBlockForBlockStub implements ElectionDetailsTestData {
	public static GetVoterBlockForBlock stub() {
		GetVoterBlockForBlock mock = mock(GetVoterBlockForBlock.class);
		when(mock.apply(ELECTION_DETAILS, 4)).thenReturn(1);
		when(mock.apply(ELECTION_DETAILS, 14)).thenReturn(4);
		when(mock.apply(ELECTION_DETAILS, 11)).thenReturn(3);
		return mock;
	}
}
