package civitas.common.mix.capabilityelementrevelation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.mix.capabilitymixrevelation.MixCapabilityElementRevelationTestData;

public class VerifyMixCapabilityElementRevelationStub
		implements MixCapabilityElementRevelationTestData {

	public static VerifyMixCapabilityElementRevelation stub() {
		VerifyMixCapabilityElementRevelation mock = mock(
				VerifyMixCapabilityElementRevelation.class);
		when(mock.apply(eq(CAPABILITY_ELEMENT_RELEVATION_MOCK), any(), anyInt(),
				anyInt(), any(), any())).thenReturn(true);
		return mock;
	}

}
