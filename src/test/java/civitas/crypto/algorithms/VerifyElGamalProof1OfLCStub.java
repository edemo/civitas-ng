package civitas.crypto.algorithms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.concrete.ElGamal1OfLReencryptionCTestData;

public class VerifyElGamalProof1OfLCStub
		implements ElGamal1OfLReencryptionCTestData {

	public static VerifyElGamalProof1OfLC stub() {
		VerifyElGamalProof1OfLC mock = mock(VerifyElGamalProof1OfLC.class);

		when(mock.apply(EL_GAMAL_PROOF_1_OF_L, EL_GAMAL_PUBLIC_KEY, CIPHERTEXT_LIST,
				NO_OF_WELL_KNOWN_CIPHERTEXTS, EL_GAMAL_CIPHERTEXT_1_OF_L))
				.thenReturn(true);
		return mock;
	}
}
