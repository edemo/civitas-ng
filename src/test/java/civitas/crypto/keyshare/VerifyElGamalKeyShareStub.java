package civitas.crypto.keyshare;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.keys.ElGamalKeyShareTestData;

public class VerifyElGamalKeyShareStub implements ElGamalKeyShareTestData {

	public static VerifyElGamalKeyShare stub() {
		VerifyElGamalKeyShare s = mock(VerifyElGamalKeyShare.class);
		when(s.apply(EL_GAMAL_KEY_SHARE_E)).thenReturn(true);
		when(s.apply(EL_GAMAL_KEY_SHARE_EPRIME)).thenReturn(true);
		when(s.apply(EL_GAMAL_KEY_SHARE_NOT_GOOD_PUBKEY_TYPE)).thenReturn(true);
		return s;
	}
}
