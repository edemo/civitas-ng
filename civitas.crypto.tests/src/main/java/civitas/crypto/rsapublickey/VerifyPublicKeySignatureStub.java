package civitas.crypto.rsapublickey;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.crypto.CryptoException;

import civitas.bboard.common.BBPostTestData;
import civitas.common.electoralroll.ElectoralRollCapabilitySharesTestData;
import civitas.crypto.signature.SignatureTestData;

public class VerifyPublicKeySignatureStub
		implements BBPostTestData, PublicKeyTestData,
		ElectoralRollCapabilitySharesTestData, SignatureTestData {
	public static VerifyPublicKeySignature stub()
			throws NoSuchAlgorithmException, InvalidKeySpecException,
            UnsupportedEncodingException, CryptoException {
		VerifyPublicKeySignature mock = mock(VerifyPublicKeySignature.class);
		when(mock.apply(BBPOST.sig, ELECTORAL_ROLL_CAPABILITY_SHARES_XML_HASH))
				.thenReturn(true);
		when(mock.apply(SIGNATURE_OF_AUTH_NONCE_WITH_KEY, PUBLIC_KEY,
				AUTHENTICATION_NONCE)).thenReturn(true);
		when(mock.apply(SIGNATURE_OF_AUTH_NONCE_WITH_KEY, PUBLIC_KEY,
				BULLETIN_BOARD_ID)).thenReturn(true);
		when(mock.apply(BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE,
				BOARD_CLOSED_CONTENT_COMMITMENT_XML.getBytes())).thenReturn(true);
		when(mock.apply(SIGNATURE_OF_AUTH_NONCE_WITH_KEY, PUBLIC_KEY,
				AUTHENTICATION_NONCE)).thenReturn(true);
		return mock;
	}

}
