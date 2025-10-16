package civitas.crypto.algorithms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import civitas.util.BasicValuesTestData;

public class CreateFreshNonceStub implements BasicValuesTestData {
	public static CreateFreshNonce stub() {
		CreateFreshNonce mock = mock(CreateFreshNonce.class);
		when(mock.apply(BITLENGTH)).thenReturn(Arrays.copyOfRange(BYTES, 0, 8));
		when(mock.apply(BITLENGTH - 1)).thenReturn(Arrays.copyOfRange(BYTES, 0, 8));
		return mock;
	}
}
