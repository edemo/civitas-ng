package civitas.common.election;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetBlockNameStub implements ElectionTestData {
	public static GetBlockName stub() {
		GetBlockName mock = mock(GetBlockName.class);
		when(mock.apply(ELECTION_DETAILS, 14)).thenReturn(BLOCKNAME_14);
		return mock;
	}
}
