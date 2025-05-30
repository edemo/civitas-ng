package civitas.crypto.proof1ofl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionCTestData;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorCTestData;

public class ConstructElGamalProof1OfLStub implements
		ElGamal1OfLReencryptionCTestData, ElGamalReencryptFactorCTestData {

	public static ConstructElGamalProof1OfL stub() {
		ConstructElGamalProof1OfL mock = mock(ConstructElGamalProof1OfL.class);
		when(mock.apply(EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST.ciphertexts,
				NO_OF_WELL_KNOWN_CIPHERTEXTS, MY_CHOICE, REENCRYPTED_WELL_KNOWN_CHOICE,
				ELGAMAL_REENCRYPT_FACTOR_E)).thenReturn(EL_GAMAL_PROOF_1_OF_L);
		return mock;

	}
}
