package civitas.common;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;

import civitas.common.tabteller.TabTellerKeyShareTestData;

class ConvertToXmlStub implements TabTellerKeyShareTestData {

	public static ConvertToXml stub() throws JsonProcessingException {
		ConvertToXml mock = mock(ConvertToXml.class);
		when(mock.apply(TAB_TELLER_KEY_SHARE)).thenReturn(TAB_TELLER_KEY_SHARE_XML);
		return mock;
	}
}
