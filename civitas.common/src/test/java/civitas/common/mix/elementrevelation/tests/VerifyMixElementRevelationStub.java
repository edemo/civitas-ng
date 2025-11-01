package civitas.common.mix.elementrevelation.tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.mix.capabilitymixrevelation.tests.MixCapabilityElementRevelationTestData;
import civitas.common.mix.elementrevelation.VerifyMixElementRevelation;
import civitas.crypto.publickey.tests.ElGamalPublicKeyTestData;

public class VerifyMixElementRevelationStub
		implements MixElementRevelationTestData, MixCapabilityElementRevelationTestData, ElGamalPublicKeyTestData {
	public static VerifyMixElementRevelation stub() {
		VerifyMixElementRevelation mock = mock(VerifyMixElementRevelation.class);
		when(mock.apply(eq(MIX_ELEMENT_REVELATION_MOCK), any(), anyInt(), anyInt(), any(), any()))
				.thenReturn(true);
		when(mock.apply(
						eq(CAPABILITY_ELEMENT_RELEVATION),
						eq(EL_GAMAL_PUBLIC_KEY_EPRIME),
						anyInt(),
						anyInt(),
						any(),
						any()))
				.thenReturn(true);
		when(mock.apply(
						eq(CAPABILITY_ELEMENT_RELEVATION_LEFT),
						eq(EL_GAMAL_PUBLIC_KEY_EPRIME),
						anyInt(),
						anyInt(),
						any(),
						any()))
				.thenReturn(true);
		when(mock.apply(
						eq(CAPABILITY_ELEMENT_RELEVATION_RIGHT),
						eq(EL_GAMAL_PUBLIC_KEY_EPRIME),
						anyInt(),
						anyInt(),
						any(),
						any()))
				.thenReturn(true);
		return mock;
	}
}
