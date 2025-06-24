package civitas.crypto.rsapublickey;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.bboard.common.BBPostTestData;
import civitas.common.electoralroll.ElectoralRollCapabilitySharesTestData;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.signature.SignatureTestData;

public class VerifyPublicKeySignatureStub
		implements BBPostTestData, PublicKeyTestData,
		ElectoralRollCapabilitySharesTestData, SignatureTestData {
	public static VerifyPublicKeySignature stub() {
		VerifyPublicKeySignature mock = mock(VerifyPublicKeySignature.class);
		when(mock.apply(PUBLIC_KEY, BBPOST.sig,
				ELECTORAL_ROLL_CAPABILITY_SHARES_XML_HASH)).thenReturn(true);
		when(mock.apply(PUBLIC_KEY, SIGNATURE_OF_AUTH_NONCE_WITH_KEY,
				new PublicKeyMsg(AUTHENTICATION_NONCE))).thenReturn(true);
		when(mock.apply(PUBLIC_KEY, SIGNATURE_OF_AUTH_NONCE_WITH_KEY,
				new PublicKeyMsg(BULLETIN_BOARD_ID))).thenReturn(true);
		return mock;
	}

}
