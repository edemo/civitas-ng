package civitas.crypto.msg.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.CryptoException;
import civitas.crypto.msg.EncodeMessage;

public class EncodeMessageStub implements ElgamalMsgTestData {

	public static EncodeMessage stub() throws CryptoException {
		EncodeMessage mock = mock(EncodeMessage.class);
		when(mock.apply(RANDOMS_0, EL_GAMAL_PARAMETERS)).thenReturn(BIGINT_G.modPow(RANDOMS_0, BIGINT_P));
		when(mock.apply(1, EL_GAMAL_PARAMETERS)).thenReturn(ONE_ENCODED.m());
		when(mock.apply(2, EL_GAMAL_PARAMETERS)).thenReturn(TWO_ENCODED.m());
		return mock;
	}
}
