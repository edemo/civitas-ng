package civitas.common;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.electoralroll.ElectoralRollCapabilitySharesTestData;
import civitas.common.tabteller.TabTellerKeyShareTestData;
import jakarta.xml.bind.JAXBException;

public class ConvertToXmlStub implements TabTellerKeyShareTestData, ElectoralRollCapabilitySharesTestData {

	public static ConvertToXml stub() throws JAXBException {
		ConvertToXml mock = mock(ConvertToXml.class);
		when(mock.apply(TAB_TELLER_KEY_SHARE)).thenReturn(TAB_TELLER_KEY_SHARE_XML);
		when(mock.apply(ELECTORAL_ROLL_CAPABILITY_SHARES)).thenReturn(ELECTORAL_ROLL_CAPABILITY_SHARES_XML);
		return mock;
	}
}
