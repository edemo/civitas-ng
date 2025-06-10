package civitas.common.mix.elementrevelation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VerifyMixElementRevelationStub implements MixElementRevelationTestData {
	public static VerifyMixElementRevelation stub() {
		VerifyMixElementRevelation mock = mock(VerifyMixElementRevelation.class);
		when(mock.apply(eq(MIX_ELEMENT_REVELATION_MOCK), any(), anyInt(), anyInt(),
				any(), any())).thenReturn(true);
		return mock;
	}
}
