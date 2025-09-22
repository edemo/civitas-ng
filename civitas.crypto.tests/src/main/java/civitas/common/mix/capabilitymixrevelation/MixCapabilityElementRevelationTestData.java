package civitas.common.mix.capabilitymixrevelation;

import static org.mockito.Mockito.mock;

import java.util.Base64;

import civitas.common.mix.capabilityelementrevelation.MixCapabilityElementRevelation;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorTestData;

public interface MixCapabilityElementRevelationTestData
		extends ElGamalReencryptFactorTestData {

	byte[] NONCE = BYTES;
	int MAPPING = 1;
	MixCapabilityElementRevelation CAPABILITY_ELEMENT_RELEVATION_MOCK = mock(
			MixCapabilityElementRevelation.class);
	MixCapabilityElementRevelation CAPABILITY_ELEMENT_RELEVATION = new MixCapabilityElementRevelation(
			1, NONCE, ELGAMAL_REENCRYPT_FACTOR_EPRIME);

	byte[] CAPABILITY_ELEMENT_RELEVATION_RIGHT_NONCE = "nonce_right".getBytes();
	byte[] CAPABILITY_ELEMENT_RELEVATION_LEFT_NONCE = "nonce_left".getBytes();
	int CAPABILITY_ELEMENT_RELEVATION_LEFT_MAPPING = 0;
	int CAPABILITY_ELEMENT_RELEVATION_RIGHT_MAPPING = 1;

	byte[] CAPABILITY_ELEMENT_RELEVATION_RIGHT_HASH = Base64.getDecoder()
			.decode("bRJm0nLPKMbXxhCoKbymzOUfYlK5T5ffjUNjhvm40C0=");

	byte[] CAPABILITY_ELEMENT_RELEVATION_LEFT_HASH = Base64.getDecoder()
			.decode("nbuVKzKGSCyUQ3uS29JzAG22s3ngrPqeLNr/jMcJRDU=");

	MixCapabilityElementRevelation CAPABILITY_ELEMENT_RELEVATION_LEFT = new MixCapabilityElementRevelation(
			CAPABILITY_ELEMENT_RELEVATION_LEFT_MAPPING,
			CAPABILITY_ELEMENT_RELEVATION_LEFT_NONCE,
			ELGAMAL_REENCRYPT_FACTOR_EPRIME);
	MixCapabilityElementRevelation CAPABILITY_ELEMENT_RELEVATION_RIGHT = new MixCapabilityElementRevelation(
			CAPABILITY_ELEMENT_RELEVATION_RIGHT_MAPPING,
			CAPABILITY_ELEMENT_RELEVATION_RIGHT_NONCE,
			ELGAMAL_REENCRYPT_FACTOR_EPRIME);

}
