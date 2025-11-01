package civitas.crypto.ciphertext.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.crypto.proof1ofl.tests.ElGamalProof1OfLTestData;
import civitas.crypto.votecapabilityshare.tests.VoteCapabilityShareTestData;

public class ElGamalEncryptStub implements ElGamalProof1OfLTestData, VoteCapabilityShareTestData {

	public static ElGamalEncrypt stub() {
		ElGamalEncrypt mock = mock(ElGamalEncrypt.class);
		for (int i = 0; i < VOTE_CAPABILITIES.size(); i++) {
			when(mock.apply(EL_GAMAL_PUBLIC_KEY_E, VOTE_CAPABILITIES.get(i), ELGAMAL_REENCRYPT_FACTOR_E))
					.thenReturn(ENCRYPTED_VOTE_CAPABILITIES.get(i));
			when(mock.apply(EL_GAMAL_PUBLIC_KEY_E, VOTE_CAPABILITIES.get(i), ELGAMAL_REENCRYPT_FACTOR_EPRIME))
					.thenReturn(ENCRYPTED_VOTE_CAPABILITIES_WITH_EPRIME.get(i));
			when(mock.apply(EL_GAMAL_PUBLIC_KEY_E, VOTE_CAPABILITY_SHARES[i], ELGAMAL_REENCRYPT_FACTOR_E))
					.thenReturn(ENCRYPTED_VOTE_CAPABILITIES.get(i));
			when(mock.apply(EL_GAMAL_PUBLIC_KEY_EPRIME, VOTE_CAPABILITY_SHARES[i], ELGAMAL_REENCRYPT_FACTOR_E))
					.thenReturn(ENCRYPTED_VOTE_CAPABILITIES_WITH_EPRIME.get(i));
			when(mock.apply(EL_GAMAL_PUBLIC_KEY_E, VOTE_CAPABILITY_SHARES[i], ELGAMAL_REENCRYPT_FACTOR_E))
					.thenReturn(PROOF_EPRIMES.get(i));
		}
		when(mock.apply(EL_GAMAL_PUBLIC_KEY_E, ONE_ENCODED, ENCRYPT_FACTOR_ZERO))
				.thenReturn(EL_GAMAL_CIPHERTEXT_ONE);
		when(mock.apply(EL_GAMAL_PUBLIC_KEY_E, TWO_ENCODED, ENCRYPT_FACTOR_ZERO))
				.thenReturn(EL_GAMAL_CIPHERTEXT_TWO);
		return mock;
	}
}
