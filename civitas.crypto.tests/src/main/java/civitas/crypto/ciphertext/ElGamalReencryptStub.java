package civitas.crypto.ciphertext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.VoteChoice;
import civitas.crypto.proof1ofl.ElGamalProof1OfLTestData;

public class ElGamalReencryptStub implements ElGamalProof1OfLTestData {

	public static ElGamalReencrypt stub() {
		ElGamalReencrypt mock = mock(ElGamalReencrypt.class);
		for (int i = 0; i < REENCRYPTED_VOTE_CAPABILITIES.size(); i++) {
			when(mock.apply(EL_GAMAL_PUBLIC_KEY_EPRIME,
					ENCRYPTED_SIGNED_VOTE_CAPABILITIES.get(i),
					ELGAMAL_REENCRYPT_FACTOR_EPRIME))
					.thenReturn(REENCRYPTED_VOTE_CAPABILITIES.get(i));
			when(mock.apply(EL_GAMAL_PUBLIC_KEY_E,
					ENCRYPTED_SIGNED_VOTE_CAPABILITIES.get(i),
					ELGAMAL_REENCRYPT_FACTOR_EPRIME))
					.thenReturn(REENCRYPTED_VOTE_CAPABILITIES_WITH_KEY_E.get(i));
		}
		when(mock.apply(EL_GAMAL_PUBLIC_KEY_E,
				CIPHERTEXT_LIST.get(MY_CHOICE.ordinal()), ELGAMAL_REENCRYPT_FACTOR_E))
				.thenReturn(REENCRYPTED_WELL_KNOWN_CHOICE);
		when(mock.apply(EL_GAMAL_PUBLIC_KEY_EPRIME,
				CIPHERTEXT_LIST.get(VoteChoice.I_BEATS_J.ordinal()),
				ELGAMAL_REENCRYPT_FACTOR_E)).thenReturn(CIPHERTEXT_E);
		return mock;
	}
}
