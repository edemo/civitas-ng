package civitas.crypto.ciphertext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.CommonConstants;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionTestData;

public class ElGamalReencryptStub implements ElGamal1OfLReencryptionTestData {

	public static ElGamalReencrypt stub() {
		ElGamalReencrypt mock = mock(ElGamalReencrypt.class);
		for (int i = 0; i < REENCRYPTED_VOTE_CAPABILITIES.size(); i++) {
			when(mock.apply(EL_GAMAL_PUBLIC_KEY_EPRIME,
					ENCRYPTED_VOTE_CAPABILITIES.get(i), ELGAMAL_REENCRYPT_FACTOR_EPRIME))
					.thenReturn(REENCRYPTED_VOTE_CAPABILITIES.get(i));
			when(mock.apply(EL_GAMAL_PUBLIC_KEY_E, ENCRYPTED_VOTE_CAPABILITIES.get(i),
					ELGAMAL_REENCRYPT_FACTOR_EPRIME))
					.thenReturn(REENCRYPTED_VOTE_CAPABILITIES_WITH_KEY_E.get(i));
		}
		when(mock.apply(EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST.get(MY_CHOICE),
				ELGAMAL_REENCRYPT_FACTOR_E)).thenReturn(REENCRYPTED_WELL_KNOWN_CHOICE);
		when(mock.apply(EL_GAMAL_PUBLIC_KEY_EPRIME,
				CIPHERTEXT_LIST.get(CommonConstants.VOTE_CHOICE_I_BEATS_J),
				ELGAMAL_REENCRYPT_FACTOR_E)).thenReturn(REENCRYPTED_WELL_KNOWN_CHOICE);
		return mock;
	}
}
