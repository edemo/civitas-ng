package civitas.crypto.proof1ofl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionTestData;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorTestData;

public class ConstructElGamalProof1OfLStub
		implements ElGamal1OfLReencryptionTestData, ElGamalReencryptFactorTestData {

	public static ConstructElGamalProof1OfL stub() {
		ConstructElGamalProof1OfL mock = mock(ConstructElGamalProof1OfL.class);
		when(mock.apply(EL_GAMAL_PUBLIC_KEY_E, CIPHERTEXT_LIST,
				NO_OF_WELL_KNOWN_CIPHERTEXTS, MY_CHOICE.ordinal(),
				REENCRYPTED_WELL_KNOWN_CHOICE, ELGAMAL_REENCRYPT_FACTOR_E))
				.thenReturn(EL_GAMAL_PROOF_1_OF_L);
		return mock;

	}
}
