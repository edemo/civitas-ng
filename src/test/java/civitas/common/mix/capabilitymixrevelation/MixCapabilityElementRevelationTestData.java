package civitas.common.mix.capabilitymixrevelation;

import static org.mockito.Mockito.mock;

import civitas.common.mix.capabilityelementrevelation.MixCapabilityElementRevelation;
import civitas.common.mix.confirmation.MixConfirmationTestData;
import civitas.common.mix.hashrevelation.MixHashRevelationTestData;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorTestData;

public interface MixCapabilityElementRevelationTestData
		extends ElGamalReencryptFactorTestData, MixHashRevelationTestData,
		MixConfirmationTestData {

	byte[] NONCE = BYTES;
	int MAPPING = 1;
	MixCapabilityElementRevelation CAPABILITY_ELEMENT_RELEVATION_MOCK = mock(
			MixCapabilityElementRevelation.class);
	MixCapabilityElementRevelation CAPABILITY_ELEMENT_RELEVATION = new MixCapabilityElementRevelation(
			1, NONCE, ELGAMAL_REENCRYPT_FACTOR_EPRIME);

	MixCapabilityElementRevelation CAPABILITY_ELEMENT_RELEVATION_LEFT = new MixCapabilityElementRevelation(
			0, "nonce_left".getBytes(), ELGAMAL_REENCRYPT_FACTOR_EPRIME);
	MixCapabilityElementRevelation CAPABILITY_ELEMENT_RELEVATION_RIGHT = new MixCapabilityElementRevelation(
			1, "nonce_right".getBytes(), ELGAMAL_REENCRYPT_FACTOR_EPRIME);

}
