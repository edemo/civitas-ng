package civitas.common.electionresults.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.electionresults.GetComputerForTeller;

public class GetComputerForTellerStub implements TellerTestData {

	public static GetComputerForTeller stub() {
		GetComputerForTeller mock = mock(GetComputerForTeller.class);
		when(mock.apply(TELLER_INDEX)).thenReturn(TELLER_COMPUTER);
		return mock;
	}
}
