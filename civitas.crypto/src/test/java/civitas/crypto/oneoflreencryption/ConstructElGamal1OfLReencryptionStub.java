package civitas.crypto.oneoflreencryption;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.VoteChoice;

public class ConstructElGamal1OfLReencryptionStub implements ElGamal1OfLReencryptionTestData {
	public static ConstructElGamal1OfLReencryption stub() {
		ConstructElGamal1OfLReencryption mock = mock(ConstructElGamal1OfLReencryption.class);
		for (VoteChoice choice : CHOICES) {
			when(mock.apply(eq(EL_GAMAL_PUBLIC_KEY_E), eq(CIPHERTEXT_LIST), eq(choice.ordinal()), any()))
					.thenReturn(EL_GAMAL_1_OF_L_REENCRYPTION_MAP.get(choice));
		}

		return mock;
	}
}
