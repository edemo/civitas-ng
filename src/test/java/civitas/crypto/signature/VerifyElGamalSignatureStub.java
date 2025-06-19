package civitas.crypto.signature;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.signedciphertext.ElGamalSignedCiphertextTestData;

public class VerifyElGamalSignatureStub
		implements ElGamalSignedCiphertextTestData {

	public static VerifyElGamalSignature stub() {
		VerifyElGamalSignature mock = mock(VerifyElGamalSignature.class);
		for(int i=0;i<POSTED_CAPABILITIES.length;i++)
			when(mock.apply(EL_GAMAL_PARAMETERS, POSTED_CAPABILITIES[i], VOTER_ADDITIONAL_ENV)).thenReturn(true);
		when(mock.apply(EL_GAMAL_PARAMETERS, SIGNED_CIPHERTEXT_OF_MESSAGE_WITH_FACTOR_RANDOM0_ADDITIONALENV,
				ADDITIONALENV_BYTES)).thenReturn(true);
		return mock;
	}

}
