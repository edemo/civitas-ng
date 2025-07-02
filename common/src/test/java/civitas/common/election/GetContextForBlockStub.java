package civitas.common.election;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetContextForBlockStub implements ElectionDetailsTestData {
	public static GetContextForBlock stub() {
		GetContextForBlock mock = mock(GetContextForBlock.class);
		when(mock.apply(ELECTION_DETAILS, 14)).thenReturn(2);
		when(mock.apply(ELECTION_DETAILS, 11)).thenReturn(2);
		return mock;
	}
}
