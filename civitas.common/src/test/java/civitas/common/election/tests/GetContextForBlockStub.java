package civitas.common.election.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.election.GetContextForBlock;

public class GetContextForBlockStub implements ElectionDetailsTestData {
	public static GetContextForBlock stub() {
		GetContextForBlock mock = mock(GetContextForBlock.class);
		when(mock.apply(ELECTION_DETAILS, 14)).thenReturn(2);
		when(mock.apply(ELECTION_DETAILS, 11)).thenReturn(2);
		return mock;
	}
}
