package civitas.crypto.msg;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.CryptoException;
import civitas.crypto.votecapabilityshare.VoteCapabilityShareTestData;

public class EncodeMessageStub implements VoteCapabilityShareTestData {

	public static EncodeMessage stub() throws CryptoException {
		EncodeMessage mock = mock(EncodeMessage.class);
		when(mock.apply(RANDOMS_0, EL_GAMAL_PARAMETERS))
				.thenReturn(BIGINT_G.modPow(RANDOMS_0, BIGINT_P));
		return mock;
	}
}
