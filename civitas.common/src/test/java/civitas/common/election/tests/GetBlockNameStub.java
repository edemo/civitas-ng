package civitas.common.election.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.election.GetBlockName;
import civitas.common.mix.hashrevelation.tests.MixHashRevelationTestData;

public class GetBlockNameStub implements ElectionDetailsTestData, MixHashRevelationTestData {
	public static GetBlockName stub() {
		GetBlockName mock = mock(GetBlockName.class);
		when(mock.apply(ELECTION_DETAILS, 14)).thenReturn(BLOCKNAME_14);
		return mock;
	}
}
