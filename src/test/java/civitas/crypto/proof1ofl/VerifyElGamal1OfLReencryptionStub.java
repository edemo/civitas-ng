package civitas.crypto.proof1ofl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionTestData;

public class VerifyElGamal1OfLReencryptionStub
		implements ElGamal1OfLReencryptionTestData {
	public static VerifyElGamal1OfLReencryption stub() {
		VerifyElGamal1OfLReencryption mock = mock(
				VerifyElGamal1OfLReencryption.class);
		
		when(mock.apply(EL_GAMAL_1_OF_L_REENCRYPTION, EL_GAMAL_PUBLIC_KEY_E,
				CIPHERTEXT_LIST, NO_OF_WELL_KNOWN_CIPHERTEXTS)).thenReturn(true);
		for(Integer choice:VOTE_CHOICES)
			when(mock.apply(EL_GAMAL_1_OF_L_REENCRYPTION_MAP.get(choice), EL_GAMAL_PUBLIC_KEY_E,
					CIPHERTEXT_LIST, NO_OF_WELL_KNOWN_CIPHERTEXTS)).thenReturn(true);
			
		return mock;
	}
}
