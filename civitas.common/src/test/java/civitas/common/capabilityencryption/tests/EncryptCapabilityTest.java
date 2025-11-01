package civitas.common.capabilityencryption.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import civitas.common.capabilityencryption.EncryptCapability;
import civitas.common.tests.RandomAwareTestBase;
import civitas.common.votersubmission.tests.VoterSubmissionTestData;
import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.crypto.reencryptfactor.GenerateElGamalReencryptFactor;

class EncryptCapabilityTest extends RandomAwareTestBase implements VoterSubmissionTestData {

	@InjectMocks
	EncryptCapability encryptCapability;

	@Mock
	GenerateElGamalReencryptFactor generateElGamalReencryptFactor;

	@Mock
	ElGamalEncrypt elGamalEncrypt;

	@Test
	@DisplayName(
			"""
			creates an encrypted capability
			- generates a reencrypt factor
			- encrypts the capability associated with the context using the key and the reencrypt factor
			""")
	void test() throws IllegalAccessException {
		assertEquals(
				ENCRYPT_CAPABILITY_RESULT, encryptCapability.apply(EL_GAMAL_PUBLIC_KEY_E, CAPABILITY_MAP, CONTEXT_0));
		verify(generateElGamalReencryptFactor).apply(EL_GAMAL_PARAMETERS);
		verify(elGamalEncrypt)
				.apply(EL_GAMAL_PUBLIC_KEY_E, VOTE_CAPABILITIES.getFirst(), ELGAMAL_REENCRYPT_FACTOR_EPRIME);
	}

	@Test
	@DisplayName(
			"if the capability map misses any needed capability for the vote," + "IllegalArgumentException is thrown")
	void test4() {

		assertThrows(
				IllegalArgumentException.class,
				() -> encryptCapability.apply(EL_GAMAL_PUBLIC_KEY_E, CAPABILITY_MAP, SOMESTRING));
	}
}
