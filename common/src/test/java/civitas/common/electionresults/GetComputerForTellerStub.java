package civitas.common.electionresults;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetComputerForTellerStub implements TellerTestData {

	public static GetComputerForTeller stub() {
		GetComputerForTeller mock = mock(GetComputerForTeller.class);
		when(mock.apply(TELLER_INDEX)).thenReturn(TELLER_COMPUTER);
		return mock;
	}
}
