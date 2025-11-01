package civitas.crypto.proof1ofl.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.VoteChoice;
import civitas.crypto.oneoflreencryption.tests.ElGamal1OfLReencryptionTestData;
import civitas.crypto.proof1ofl.VerifyElGamal1OfLReencryption;

public class VerifyElGamal1OfLReencryptionStub implements ElGamal1OfLReencryptionTestData {
	public static VerifyElGamal1OfLReencryption stub() {
		VerifyElGamal1OfLReencryption mock = mock(VerifyElGamal1OfLReencryption.class);

		when(mock.apply(
						EL_GAMAL_1_OF_L_REENCRYPTION,
						EL_GAMAL_PUBLIC_KEY_E,
						CIPHERTEXT_LIST,
						NO_OF_WELL_KNOWN_CIPHERTEXTS))
				.thenReturn(true);
		for (VoteChoice choice : CHOICES) {
			when(mock.apply(
							EL_GAMAL_1_OF_L_REENCRYPTION_MAP.get(choice),
							EL_GAMAL_PUBLIC_KEY_E,
							CIPHERTEXT_LIST,
							NO_OF_WELL_KNOWN_CIPHERTEXTS))
					.thenReturn(true);
		}

		return mock;
	}
}
