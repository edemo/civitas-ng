package civitas.crypto.proof1ofl.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.oneoflreencryption.tests.ElGamal1OfLReencryptionTestData;
import civitas.crypto.proof1ofl.VerifyElGamalProof1OfL;

public class VerifyElGamalProof1OfLStub implements ElGamal1OfLReencryptionTestData {

	public static VerifyElGamalProof1OfL stub() {
		VerifyElGamalProof1OfL mock = mock(VerifyElGamalProof1OfL.class);

		when(mock.apply(
						EL_GAMAL_PROOF_1_OF_L,
						EL_GAMAL_PUBLIC_KEY_E,
						CIPHERTEXT_LIST,
						NO_OF_WELL_KNOWN_CIPHERTEXTS,
						REENCRYPTED_WELL_KNOWN_CHOICE))
				.thenReturn(true);
		return mock;
	}
}
