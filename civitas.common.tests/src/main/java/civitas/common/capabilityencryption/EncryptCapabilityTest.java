package civitas.common.capabilityencryption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.RandomAwareTestBase;
import civitas.common.votersubmission.VoterSubmissionTestData;

class EncryptCapabilityTest extends RandomAwareTestBase
		implements VoterSubmissionTestData {

	@InjectMocks
	EncryptCapability encryptCapability;

	@Test
	@DisplayName("""
			creates an encrypted capability
			 - generates a reencrypt factor
			 - encrypts the capability associated with the context using the key and the reencrypt factor
			""")
	void test() {
		CapabilityEncryption encryptCapabilityResult = new CapabilityEncryption(
				ELGAMAL_REENCRYPT_FACTOR_EPRIME,
				ENCRYPTED_VOTE_CAPABILITIES_WITH_EPRIME.getFirst());
		assertEquals(encryptCapabilityResult, encryptCapability
				.apply(EL_GAMAL_PUBLIC_KEY_E, CAPABILITY_MAP, CONTEXT_0));
		verify(encryptCapability.generateElGamalReencryptFactor)
				.apply(EL_GAMAL_PARAMETERS);
		verify(encryptCapability.elGamalEncrypt).apply(EL_GAMAL_PUBLIC_KEY_E,
				VOTE_CAPABILITIES.getFirst(), ELGAMAL_REENCRYPT_FACTOR_EPRIME);
	}

	@Test
	@DisplayName("if the capability map misses any needed capability for the vote,"
			+ "IllegalArgumentException is thrown")
	void test4() {

		assertThrows(IllegalArgumentException.class, () -> encryptCapability
				.apply(EL_GAMAL_PUBLIC_KEY_E, CAPABILITY_MAP, SOMESTRING));
	}

}
