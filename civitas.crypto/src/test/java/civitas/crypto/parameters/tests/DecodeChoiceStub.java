package civitas.crypto.parameters.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.VoteChoice;
import civitas.crypto.CryptoException;
import civitas.crypto.parameters.DecodeChoice;
import civitas.util.CivitasBigIntegerFactory;

public class DecodeChoiceStub implements ElGamalParametersTestData {
	public static DecodeChoice stub() throws CryptoException {
		DecodeChoice mock = mock(DecodeChoice.class);
		when(mock.apply(DECODEMAP, BIGINT_A)).thenThrow(IllegalArgumentException.class);
		for (VoteChoice choice : CHOICES) {
			when(mock.apply(
							DECODEMAP,
							CivitasBigIntegerFactory.obtain(choice.ordinal()).modMultiply(BIGINT_G, BIGINT_P)))
					.thenReturn(choice);
		}
		return mock;
	}
}
