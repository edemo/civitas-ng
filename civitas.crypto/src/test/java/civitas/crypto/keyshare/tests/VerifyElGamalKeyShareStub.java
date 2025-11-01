package civitas.crypto.keyshare.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.keys.tests.ElGamalKeyShareTestData;
import civitas.crypto.keyshare.VerifyElGamalKeyShare;

public class VerifyElGamalKeyShareStub implements ElGamalKeyShareTestData {

	public static VerifyElGamalKeyShare stub() {
		VerifyElGamalKeyShare s = mock(VerifyElGamalKeyShare.class);
		when(s.apply(EL_GAMAL_KEY_SHARE_E)).thenReturn(true);
		when(s.apply(EL_GAMAL_KEY_SHARE_EPRIME)).thenReturn(true);
		return s;
	}
}
